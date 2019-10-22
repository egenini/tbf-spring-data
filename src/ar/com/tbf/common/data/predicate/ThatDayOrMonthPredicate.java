package ar.com.tbf.common.data.predicate;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class ThatDayOrMonthPredicate implements SpecificPredicate{

	private ThatDayPredicate   thatDayPredicate;
	private ThatMonthPredicate ThatMonthPredicate;
	
	public ThatDayOrMonthPredicate( DateFormat dateformat, DateTimeFormatter monthformat ) {

		thatDayPredicate   = new ThatDayPredicate(   dateformat  );
		ThatMonthPredicate = new ThatMonthPredicate( monthformat );
	}

	public Predicate build( final From<?, ?> from, CriteriaBuilder builder, String key, Object value ) {

		Predicate predicate = thatDayPredicate.build(from, builder, key, value);
		
		if( predicate == null ) {
			
			predicate = ThatMonthPredicate.build(from, builder, key, value);
		}
		
		return predicate;
	}
}
