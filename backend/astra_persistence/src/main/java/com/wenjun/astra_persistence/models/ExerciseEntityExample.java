package com.wenjun.astra_persistence.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExerciseEntityExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNull() {
            addCriterion("date_created is null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNotNull() {
            addCriterion("date_created is not null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedEqualTo(Date value) {
            addCriterion("date_created =", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotEqualTo(Date value) {
            addCriterion("date_created <>", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThan(Date value) {
            addCriterion("date_created >", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("date_created >=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThan(Date value) {
            addCriterion("date_created <", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThanOrEqualTo(Date value) {
            addCriterion("date_created <=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIn(List<Date> values) {
            addCriterion("date_created in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotIn(List<Date> values) {
            addCriterion("date_created not in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedBetween(Date value1, Date value2) {
            addCriterion("date_created between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotBetween(Date value1, Date value2) {
            addCriterion("date_created not between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedIsNull() {
            addCriterion("date_updated is null");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedIsNotNull() {
            addCriterion("date_updated is not null");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedEqualTo(Date value) {
            addCriterion("date_updated =", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedNotEqualTo(Date value) {
            addCriterion("date_updated <>", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedGreaterThan(Date value) {
            addCriterion("date_updated >", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedGreaterThanOrEqualTo(Date value) {
            addCriterion("date_updated >=", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedLessThan(Date value) {
            addCriterion("date_updated <", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedLessThanOrEqualTo(Date value) {
            addCriterion("date_updated <=", value, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedIn(List<Date> values) {
            addCriterion("date_updated in", values, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedNotIn(List<Date> values) {
            addCriterion("date_updated not in", values, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedBetween(Date value1, Date value2) {
            addCriterion("date_updated between", value1, value2, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andDateUpdatedNotBetween(Date value1, Date value2) {
            addCriterion("date_updated not between", value1, value2, "dateUpdated");
            return (Criteria) this;
        }

        public Criteria andIndexIsNull() {
            addCriterion("index is null");
            return (Criteria) this;
        }

        public Criteria andIndexIsNotNull() {
            addCriterion("index is not null");
            return (Criteria) this;
        }

        public Criteria andIndexEqualTo(Integer value) {
            addCriterion("index =", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotEqualTo(Integer value) {
            addCriterion("index <>", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThan(Integer value) {
            addCriterion("index >", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("index >=", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThan(Integer value) {
            addCriterion("index <", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThanOrEqualTo(Integer value) {
            addCriterion("index <=", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexIn(List<Integer> values) {
            addCriterion("index in", values, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotIn(List<Integer> values) {
            addCriterion("index not in", values, "index");
            return (Criteria) this;
        }

        public Criteria andIndexBetween(Integer value1, Integer value2) {
            addCriterion("index between", value1, value2, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("index not between", value1, value2, "index");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andRepsIsNull() {
            addCriterion("reps is null");
            return (Criteria) this;
        }

        public Criteria andRepsIsNotNull() {
            addCriterion("reps is not null");
            return (Criteria) this;
        }

        public Criteria andRepsEqualTo(Integer value) {
            addCriterion("reps =", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsNotEqualTo(Integer value) {
            addCriterion("reps <>", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsGreaterThan(Integer value) {
            addCriterion("reps >", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsGreaterThanOrEqualTo(Integer value) {
            addCriterion("reps >=", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsLessThan(Integer value) {
            addCriterion("reps <", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsLessThanOrEqualTo(Integer value) {
            addCriterion("reps <=", value, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsIn(List<Integer> values) {
            addCriterion("reps in", values, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsNotIn(List<Integer> values) {
            addCriterion("reps not in", values, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsBetween(Integer value1, Integer value2) {
            addCriterion("reps between", value1, value2, "reps");
            return (Criteria) this;
        }

        public Criteria andRepsNotBetween(Integer value1, Integer value2) {
            addCriterion("reps not between", value1, value2, "reps");
            return (Criteria) this;
        }

        public Criteria andSetsIsNull() {
            addCriterion("sets is null");
            return (Criteria) this;
        }

        public Criteria andSetsIsNotNull() {
            addCriterion("sets is not null");
            return (Criteria) this;
        }

        public Criteria andSetsEqualTo(Integer value) {
            addCriterion("sets =", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsNotEqualTo(Integer value) {
            addCriterion("sets <>", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsGreaterThan(Integer value) {
            addCriterion("sets >", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsGreaterThanOrEqualTo(Integer value) {
            addCriterion("sets >=", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsLessThan(Integer value) {
            addCriterion("sets <", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsLessThanOrEqualTo(Integer value) {
            addCriterion("sets <=", value, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsIn(List<Integer> values) {
            addCriterion("sets in", values, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsNotIn(List<Integer> values) {
            addCriterion("sets not in", values, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsBetween(Integer value1, Integer value2) {
            addCriterion("sets between", value1, value2, "sets");
            return (Criteria) this;
        }

        public Criteria andSetsNotBetween(Integer value1, Integer value2) {
            addCriterion("sets not between", value1, value2, "sets");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(Integer value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(Integer value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(Integer value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(Integer value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(Integer value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<Integer> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<Integer> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(Integer value1, Integer value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(Integer value1, Integer value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdIsNull() {
            addCriterion("workout_log_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdIsNotNull() {
            addCriterion("workout_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdEqualTo(Long value) {
            addCriterion("workout_log_id =", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdNotEqualTo(Long value) {
            addCriterion("workout_log_id <>", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdGreaterThan(Long value) {
            addCriterion("workout_log_id >", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("workout_log_id >=", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdLessThan(Long value) {
            addCriterion("workout_log_id <", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdLessThanOrEqualTo(Long value) {
            addCriterion("workout_log_id <=", value, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdIn(List<Long> values) {
            addCriterion("workout_log_id in", values, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdNotIn(List<Long> values) {
            addCriterion("workout_log_id not in", values, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdBetween(Long value1, Long value2) {
            addCriterion("workout_log_id between", value1, value2, "workoutLogId");
            return (Criteria) this;
        }

        public Criteria andWorkoutLogIdNotBetween(Long value1, Long value2) {
            addCriterion("workout_log_id not between", value1, value2, "workoutLogId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
