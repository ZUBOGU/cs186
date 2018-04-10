package simpledb;

public class QueryPlans {

	public QueryPlans(){
	}

	//SELECT * FROM T1, T2 WHERE T1.column0 = T2.column0;
	public Operator queryOne(DbIterator t1, DbIterator t2) {
		// IMPLEMENT ME
		JoinPredicate pred = new JoinPredicate(0, Predicate.Op.EQUALS, 0);
		Join t3 = new Join(pred, t1, t2);
		return t3;
	}

	//SELECT * FROM T1, T2 WHERE T1. column0 > 1 AND T1.column1 = T2.column1;
	public Operator queryTwo(DbIterator t1, DbIterator t2) {
		// IMPLEMENT ME
		Predicate pred = new Predicate(1, Predicate.Op.GREATER_THAN, new IntField(1));
		Filter t3 = new Filter(pred, t1);
		JoinPredicate pred1 = new JoinPredicate(1, Predicate.Op.EQUALS, 1);
		Join t4 = new Join(pred1, t3, t2);
		return t4;
	}

	//SELECT column0, MAX(column1) FROM T1 WHERE column2 > 1 GROUP BY column0;
	public Operator queryThree(DbIterator t1) {
		// IMPLEMENT ME
		Predicate pred = new Predicate(2, Predicate.Op.GREATER_THAN, new IntField(1));
		Filter t2 = new Filter(pred, t1);
		Aggregate t3 = new Aggregate(t2, 1, 0, Aggregator.Op.MAX);
		return t3;
	}

	// SELECT ​​* FROM T1, T2
	// WHERE T1.column0 < (SELECT COUNT(*​​) FROM T3)
	// AND T2.column0 = (SELECT AVG(column0) FROM T3)
	// AND T1.column1 >= T2. column1
	// ORDER BY T1.column0 DESC;
	public Operator queryFour(DbIterator t1, DbIterator t2, DbIterator t3) throws TransactionAbortedException, DbException {
		// IMPLEMENT ME
		Aggregate count = new Aggregate(t3, 0, -1, Aggregator.Op.COUNT);
		Aggregate avg = new Aggregate(t3, 0, -1, Aggregator.Op.AVG);

		Predicate pred = new Predicate(0, Predicate.Op.LESS_THAN, count.next().getField(0));
		Filter newt1 = new Filter(pred, t1);
		t3.rewind();
		Predicate pred1 = new Predicate(0, Predicate.Op.EQUALS, avg.next().getField(0));
		Filter newt2 = new Filter(pred1, t2);

		JoinPredicate pred2 = new JoinPredicate(1, Predicate.Op.GREATER_THAN_OR_EQ, 1);
		Join newt = new Join(pred2, newt1, newt2);

		OrderBy finalt = new OrderBy(0, false, newt);
		return finalt;
	}


}