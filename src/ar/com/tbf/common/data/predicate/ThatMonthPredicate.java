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

public class ThatMonthPredicate implements SpecificPredicate{

	private DateTimeFormatter dateformat;
	
	public ThatMonthPredicate( DateTimeFormatter dateformat ) {
		
		this.dateformat = dateformat;
	}
	
	public Predicate build( final From<?, ?> from, CriteriaBuilder builder, String key, Object value ) {
		
		Predicate predicate = null;
		
		try {
			
			Timestamp date = Timestamp.valueOf( YearMonth.parse( (CharSequence)value, dateformat).atDay(1).atStartOfDay() );
			
			Calendar init = Calendar.getInstance(  );
			
			init.setTime( date );

			Calendar end = Calendar.getInstance();
			
			end.setTime( init.getTime() );

			end.set( Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH) );
			end.set( Calendar.HOUR        , 23);
			end.set( Calendar.MINUTE      , 59);
			end.set( Calendar.SECOND      , 59);
			end.set( Calendar.MILLISECOND , 999);
						
			predicate = builder.between( from.<Date>get(key), init.getTime(), end.getTime() );

		}catch( Exception e ) {
			//e.printStackTrace();
		}
		
		return predicate;	
	}
}
