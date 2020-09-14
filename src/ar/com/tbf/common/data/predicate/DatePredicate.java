package ar.com.tbf.common.data.predicate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import ar.com.tbf.common.data.GenericPredicateBuilder;
import ar.com.tbf.common.data.SearchOperation;
import ar.com.tbf.common.data.exception.PredicateException;

public class DatePredicate implements SpecificPredicate{
	
	private DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	private DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private DateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat dateformat4 = new SimpleDateFormat("yyyy-MM");
	
	public DatePredicate( ) {
		
	}
	
	public DatePredicate( DateFormat dateFormat ) {
		
		this.dateformat = dateFormat;
	}
	
	@Override
	public Predicate build(From<?, ?> from, CriteriaBuilder builder, String key, Object value) {
		
		return null;
	}

	@Override
	public Predicate build(SearchOperation operation, From<?, ?> from, CriteriaBuilder builder, String key, Object value) {

		Predicate predicate = null;
		
		Date fecha = null;
		
		if( ! value.equals( GenericPredicateBuilder.NULL_VALUE ) ) {
			
			try {
				fecha = dateformat.parse( (String) value );
			} catch (ParseException e) {
				
				try {
					fecha = dateformat2.parse( (String) value );
				} catch (ParseException e2) {
					
					try {
						fecha = dateformat3.parse( (String) value );
					} catch (ParseException e3) {
						
						try {
							fecha = dateformat4.parse( (String) value );
						} catch (ParseException e4) {
							
							throw new PredicateException( "No se pudo interpretar la fecha "+ value +" para el atributo "+ key );
						}
					}
				}
			}
		}
		
			switch ( operation ) {
			
			case EQUALITY:
				if( value.equals( GenericPredicateBuilder.NULL_VALUE )) {
					
					predicate = builder.isNull(       from.get(key)             );
				}
				else {
					predicate = builder.equal(       from.get(key), fecha            );
				}
				break;
			case NEGATION:
				if( value.equals( GenericPredicateBuilder.NULL_VALUE )) {
					
					predicate =  builder.isNotNull(       from.get(key)            );
				}
				else {
					predicate = builder.notEqual(       from.get(key), fecha            );
				}
				break;
			case GREATER_THAN:
				predicate = builder.greaterThan(          from.get(key), fecha );
				break;
			case LESS_THAN:
				predicate =  builder.lessThan(            from.get(key), fecha );
				break;
			case GREATER_THAN_OR_EQUALS:
				predicate = builder.greaterThanOrEqualTo( from.get(key), fecha );
				break;
			case LESS_THAN_OR_EQUALS:
				predicate =  builder.lessThanOrEqualTo(   from.get(key), fecha );
				break;
			default:
				break;
			}
		
		return predicate;
	}

}
