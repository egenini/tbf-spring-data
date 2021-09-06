package ar.com.tbf.common.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import ar.com.tbf.common.data.accessibility.AtributeExpressionAccessibility;
import ar.com.tbf.common.data.accessibility.AttributePredicateHelperAccessibility;

public class GenericPredicateBuilder {

	public static final String NULL_VALUE  = "null";
	public static final String FALSE_VALUE = "false";
	public static final String TRUE_VALUE  = "true";
	
	List<Predicate> predicates = null;
	CriteriaBuilder builder;
	
	public List<Predicate> getPredicates(){
		
		return predicates;
	}
	
	public GenericPredicateBuilder withBuilder( CriteriaBuilder builder ) {
		
		this.builder = builder;
		
		return this;
	}
	
	/**
	 * Reemplaza la lista de predicados que tiene.
	 * 
	 * @param predicates
	 * @return
	 */
	public GenericPredicateBuilder withPredicates( List<Predicate> predicates ) {
		
		this.predicates = predicates;
		
		return this;
	}
	
	/**
	 * Crea la lista de predicados;
	 * 
	 * @return
	 */
	public GenericPredicateBuilder withPredicates() {
		
		this.predicates = new ArrayList<Predicate>();
		
		return this;
	}
	
	public GenericPredicateBuilder make( SearchOperation operation, From<?, ?> from, String key ,Object value ) {
		
		Predicate predicate = null;
		
		try {
			
			if( AttributePredicateHelperAccessibility.has( key ) ) {
				
				predicate = AttributePredicateHelperAccessibility.get( key ).build( operation, from, builder, key, value );
				
				if( predicate != null ) {
					
					predicates.add( predicate );
				}
				else {
					
					predicate = AttributePredicateHelperAccessibility.get( key ).build(from, builder, key, value );
					
					if( predicate != null ) {
						
						predicates.add( predicate );
					}
				}
			}
			else {
				
				Expression<?> expression = AtributeExpressionAccessibility.hasBuild(key) ? AtributeExpressionAccessibility.build(key, from, builder) : from.get(key); 
				
				switch ( operation ) {
				
				case EQUALITY:
					if( value.equals( NULL_VALUE )) {
						
						predicates.add( builder.isNull( expression      ) );
					}
					else if( value.equals( FALSE_VALUE )) {
						
						predicates.add( builder.equal( expression, false ) );
					}
					else if( value.equals( TRUE_VALUE )) {
						
						predicates.add( builder.equal( expression, true  ) );
					}
					else {
						predicates.add( builder.equal( expression, value ) );
					}
					break;
				case NEGATION:
					if( value.equals( NULL_VALUE )) {
						
						predicates.add( builder.isNotNull( expression        ) );
					}
					else if( value.equals( FALSE_VALUE )) {
						
						predicates.add( builder.notEqual(  expression, false ) );
					}
					else if( value.equals( TRUE_VALUE )) {
						
						predicates.add( builder.notEqual(  expression, true  ) );
					}
					else {
						predicates.add( builder.notEqual(  expression, value ) );
					}
					break;
				case GREATER_THAN:
					if( value instanceof String ) {
						predicates.add( builder.greaterThan( from.get(key), value.toString()     ) );
					}else if( value instanceof Date ) {
						predicates.add( builder.greaterThan( from.get(key), (Date)value          ) );						
					}else if( value instanceof Timestamp ) {
						predicates.add( builder.greaterThan( from.get(key), (Timestamp)value     ) );						
					}else if( value instanceof Integer ) {
						predicates.add( builder.greaterThan( from.get(key), (Integer)value       ) );						
					}else if( value instanceof BigDecimal ) {
						predicates.add( builder.greaterThan( from.get(key), (BigDecimal )value   ) );						
					}else if( value instanceof Double ) {
						predicates.add( builder.greaterThan( from.get(key), (Double)value        ) );						
					}else if( value instanceof Float ) {
						predicates.add( builder.greaterThan( from.get(key), (Float)value         ) );						
					}else if( value instanceof BigInteger ) {
						predicates.add( builder.greaterThan( from.get(key), (BigInteger)value    ) );						
					}else if( value instanceof Short ) {
						predicates.add( builder.greaterThan( from.get(key), (Short)value         ) );						
					}else if( value instanceof java.sql.Date ) {
						predicates.add( builder.greaterThan( from.get(key), (java.sql.Date)value ) );						
					}
					break;
				case LESS_THAN:
					if( value instanceof String ) {
						predicates.add( builder.lessThan( from.get(key), value.toString()     ) );
					}else if( value instanceof Date ) {
						predicates.add( builder.lessThan( from.get(key), (Date)value          ) );						
					}else if( value instanceof Timestamp ) {
						predicates.add( builder.lessThan( from.get(key), (Timestamp)value     ) );						
					}else if( value instanceof Integer ) {
						predicates.add( builder.lessThan( from.get(key), (Integer)value       ) );						
					}else if( value instanceof BigDecimal ) {
						predicates.add( builder.lessThan( from.get(key), (BigDecimal )value   ) );						
					}else if( value instanceof Double ) {
						predicates.add( builder.lessThan( from.get(key), (Double)value        ) );						
					}else if( value instanceof Float ) {
						predicates.add( builder.lessThan( from.get(key), (Float)value         ) );						
					}else if( value instanceof BigInteger ) {
						predicates.add( builder.lessThan( from.get(key), (BigInteger)value    ) );						
					}else if( value instanceof Short ) {
						predicates.add( builder.lessThan( from.get(key), (Short)value         ) );						
					}else if( value instanceof java.sql.Date ) {
						predicates.add( builder.lessThan( from.get(key), (java.sql.Date)value ) );						
					}
					break;
				case GREATER_THAN_OR_EQUALS:
					if( value instanceof String ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), value.toString()     ) );
					}else if( value instanceof Date ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Date)value          ) );						
					}else if( value instanceof Timestamp ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Timestamp)value     ) );						
					}else if( value instanceof Integer ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Integer)value       ) );						
					}else if( value instanceof BigDecimal ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (BigDecimal )value   ) );						
					}else if( value instanceof Double ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Double)value        ) );						
					}else if( value instanceof Float ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Float)value         ) );						
					}else if( value instanceof BigInteger ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (BigInteger)value    ) );						
					}else if( value instanceof Short ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (Short)value         ) );						
					}else if( value instanceof java.sql.Date ) {
						predicates.add( builder.greaterThanOrEqualTo( from.get(key), (java.sql.Date)value ) );						
					}
					break;
				case LESS_THAN_OR_EQUALS:
					if( value instanceof String ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), value.toString()     ) );
					}else if( value instanceof Date ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Date)value          ) );						
					}else if( value instanceof Timestamp ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Timestamp)value     ) );						
					}else if( value instanceof Integer ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Integer)value       ) );						
					}else if( value instanceof BigDecimal ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (BigDecimal )value   ) );						
					}else if( value instanceof Double ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Double)value        ) );						
					}else if( value instanceof Float ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Float)value         ) );						
					}else if( value instanceof BigInteger ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (BigInteger)value    ) );						
					}else if( value instanceof Short ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (Short)value         ) );						
					}else if( value instanceof java.sql.Date ) {
						predicates.add( builder.lessThanOrEqualTo( from.get(key), (java.sql.Date)value ) );						
					}
					break;
				case LIKE:
					try {
						predicates.add( builder.like( (Expression<String>) expression, "%"+ value +"%" ) );
					}catch(Exception e) {}
					break;
				case STARTS_WITH:
					try {
						predicates.add( builder.like( (Expression<String>) expression, value +"%"      ) );
					}catch(Exception e) {}
					break;
				case ENDS_WITH:
					try {
						predicates.add( builder.like( (Expression<String>) expression, "%"+ value      ) );
					}catch(Exception e) {}
					break;
				case CONTAINS:
					try {
						predicates.add( builder.like( (Expression<String>) expression, "%"+ value +"%" ) );
					}catch(Exception e) {}
					break;
				default:
					break;
				}
			}

		}catch(RuntimeException re) {

			if( ! AttributePredicateHelperAccessibility.isSilentMode() ) {
				
				throw re;
			}
		}
		return this;
	}
	
	public Predicate build() {
		
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	public Predicate build(List<Predicate> predicates) {
		
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
