package ar.com.tbf.common.data.predicate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import ar.com.tbf.common.data.SearchOperation;

public class ThatYearMonthLastDayOfMonthPredicate implements SpecificPredicate{

	private DateTimeFormatter dateformat;
	
	public ThatYearMonthLastDayOfMonthPredicate( DateTimeFormatter dateformat, Calendar from ) {
		
		this.dateformat = dateformat;
	}
	
	public Predicate build( final From<?, ?> from, CriteriaBuilder builder, String key, Object value ) {
		
		Predicate predicate = null;
		
		try {
			
			Timestamp date = Timestamp.valueOf( YearMonth.parse( (CharSequence ) value, dateformat).atEndOfMonth().atTime(0, 0, 0) );
			
			Calendar init = Calendar.getInstance(  );
			
			init.setTime( date );

			Calendar end = Calendar.getInstance();
						
			predicate = builder.between( from.<Date>get(key), init.getTime(), end.getTime() );

		}catch( Exception e ) {
			//e.printStackTrace();
		}
		
		return predicate;	
	}

	@Override
	public Predicate build(SearchOperation operation, From<?, ?> from, CriteriaBuilder builder, String key,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
