package net.gesher.rides.server.dal;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static net.gesher.rides.server.dal.ExpressionBuilderHelper.*;

public abstract class AbstractDal {
    protected DbSessionManager sessionManager;

    public AbstractDal(DbSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    /**
     * builds a {#Predicate} out of an {#ExpressionBuilderHelper}
     * @param builder
     * @param expressionBuilderHelper
     * @param root
     * @return
     */
    private Predicate buildPredicate(CriteriaBuilder builder,
                                     ExpressionBuilderHelper expressionBuilderHelper,
                                     Root root){
        switch (expressionBuilderHelper.getPredicateName()){
            case PREDICATE_EQUALS:
                return builder.equal(
                        root.get(expressionBuilderHelper.getFieldName()).as(expressionBuilderHelper.getFieldType()),
                        expressionBuilderHelper.getValue()
                );
            case PREDICATE_GREATER_THAN:
                return builder.gt(
                        root.get(expressionBuilderHelper.getFieldName()).as(expressionBuilderHelper.getFieldType()),
                        (Number)expressionBuilderHelper.getValue()
                );
            case PREDICATE_LESS_THAN:
                return builder.lt(
                        root.get(expressionBuilderHelper.getFieldName()).as(expressionBuilderHelper.getFieldType()),
                        (Number)expressionBuilderHelper.getValue()
                );
            case PREDICATE_BETWEEN:
                return builder.between(
                        root.get(expressionBuilderHelper.getFieldName()).as(expressionBuilderHelper.getFieldType()),
                        (Comparable)expressionBuilderHelper.getValue(),
                        (Comparable)expressionBuilderHelper.getAdditionalValue()
                );
        }
        throw new IllegalArgumentException("Unknown predicate name: " + expressionBuilderHelper.getPredicateName());
    }

    /**
     * generic method for retrieving lists of {#Ride}
     * @param constraints
     * @return
     */
    protected <T> List<T> getEntityList(Class<T> entityClass, List<ExpressionBuilderHelper> constraints){
        Session session = sessionManager.getSessionFactoryInstance().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        if(constraints != null && constraints.size() > 0){
            addCriteriaToQuery(criteria, builder, constraints, root);
        }
        criteria.select( root );

        Query query = session.createQuery(criteria);
        return query.getResultList();
    }

    /**
     * method for attaching conditional criteria to queries
     * @param query
     * @param builder
     * @param expressionBuilderHelpers
     * @param root
     */
    protected void addCriteriaToQuery(CriteriaQuery query,
                                    CriteriaBuilder builder,
                                    List<ExpressionBuilderHelper> expressionBuilderHelpers,
                                    Root root){
        if(expressionBuilderHelpers.size() == 1){
            ExpressionBuilderHelper ex = expressionBuilderHelpers.get(0);
            query.where(buildPredicate(builder, expressionBuilderHelpers.get(0), root));
            return;
        }

        Predicate[] predicates = new Predicate[expressionBuilderHelpers.size()];
        for(int i = 0; i < expressionBuilderHelpers.size(); i++) {
            // if there are multiple predicates, add them all with an and statement
            ExpressionBuilderHelper ex = expressionBuilderHelpers.get(i);
            predicates[i] = buildPredicate(builder, ex, root);
        }
        query.where(builder.and(predicates));
    }
}
