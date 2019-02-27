package test_constant;

import static org.junit.Assert.*;

import org.junit.Test;

import constant.WizardConstant;

public class TestWizardConstant {

	private WizardConstant wConstant;
	
	
	@Test
	public void testWizardConstant() {
		wConstant = new WizardConstant(70, 30, 10, 5, 10, 20);
		
		int expected = 70;
		int result = wConstant.getMaxHealth();
		assertEquals(expected, result);
		
		expected = 30;
		result = wConstant.getInitArmor();
		assertEquals(expected, result);
		
		expected = 10;
		result = wConstant.getBaseMove();
		assertEquals(expected, result);
		
		expected = 5;
		result = wConstant.getBaseRange();
		assertEquals(expected, result);
		
		expected = 10;
		result = wConstant.getBaseMana();
		assertEquals(expected, result);
		
		expected = 20;
		result = wConstant.getNbInitCard();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException1() {
		wConstant = new WizardConstant(-70, 30, 10, 5, 10, 20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException2() {
		wConstant = new WizardConstant(70, -30, 10, 5, 10, 20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException3() {
		wConstant = new WizardConstant(70, 30, -10, 5, 10, 20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException4() {
		wConstant = new WizardConstant(70, 30, 10, -5, 10, 20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException5() {
		wConstant = new WizardConstant(70, 30, 10, 5, -10, 20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConstantWizardException6() {
		wConstant = new WizardConstant(70, 30, 10, 5, 10, -20);
	}
	
	

}
