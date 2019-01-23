package test_target;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import target.Target;
import target.TargetConstraint;
import target.TargetType;

public class TestTarget
{
	private TargetConstraint[] constraints;
	private Target target;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		constraints = new TargetConstraint[]
				{
						TargetConstraint.NOTYOU,
						TargetConstraint.NOTALLY
				};
		
		target = new Target(constraints, TargetType.CHOICE);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	@Test
	public final void testTargetTargetConstraintEmpty()
	{
		target = new Target(new TargetConstraint[0], TargetType.CHOICE);
		int expected = 0;
		int result = target.getConstraints().length;
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTargetException1()
	{
		target = new Target(null, TargetType.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTargetException2()
	{
		constraints = new TargetConstraint[]
				{
						TargetConstraint.NOTYOU,
						TargetConstraint.NOTALLY,
						TargetConstraint.NOTYOU
				};
		
		target = new Target(constraints, TargetType.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTargetException3()
	{
		target = new Target(constraints, null);
	}

	@Test
	public final void testGetConstraints()
	{
		TargetConstraint[] expected = constraints;
		TargetConstraint[] result = target.getConstraints();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

	@Test
	public final void testGetType()
	{
		TargetType expected = TargetType.CHOICE;
		TargetType result = target.getType();
		assertEquals(expected, result);
	}

}
