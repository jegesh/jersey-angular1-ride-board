package net.gesher.rides.server.dal;

import net.gesher.rides.server.entity.Ride;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.*;

import static net.gesher.rides.server.dal.ExpressionBuilderHelper.*;

public class RideDal {
    private DbSessionManager sessionManager;

    public RideDal(DbSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    /**
     *
     * @param r
     * @return null if insert failed, otherwise id of created record
     */
    public Ride insertRide(Ride r){
        Serializable result = sessionManager.getSessionFactoryInstance().openSession().save(r);
        if((Long)result < 1) return null;
        return r;
    }

    /**
     * retrieves all rides scheduled to leave today
     * @return
     */
    public List<Ride> getSingleDayRides(Date target){

        List<ExpressionBuilderHelper> constraints = new ArrayList<>();
        ExpressionBuilderHelper exBuilder = new ExpressionBuilderHelper("departureDate", Date.class, DateUtils.getEndOfPreviousDay(target), PREDICATE_BETWEEN);
        exBuilder.setAdditionalValue(DateUtils.getStartOfNextDay(target));
        constraints.add(exBuilder);
        return getRideList(constraints);

    }

    /**
     * generic method for retrieving lists of {#Ride}
     * @param constraints
     * @return
     */
    private List<Ride> getRideList(List<ExpressionBuilderHelper> constraints){
        Session session = sessionManager.getSessionFactoryInstance().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Ride> criteria = builder.createQuery(Ride.class);
        Root<Ride> root = criteria.from(Ride.class);
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
    private void addCriteriaToQuery(CriteriaQuery query,
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

}
