package ar.com.tbf.common.data.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import ar.com.tbf.common.data.SearchOperation;

public interface SpecificPredicate {

	public Predicate build( final From<?, ?> from, CriteriaBuilder builder, String key, Object value );
	public Predicate build( SearchOperation operation, final From<?, ?> from, CriteriaBuilder builder, String key, Object value );
}