package target;

import java.util.ArrayList;

import com.google.common.base.Preconditions;

public class Target
{
	private TargetConstraint[] constraints;
	private TargetType type;
	
	public Target(TargetConstraint[] constraints, TargetType type)
	{
		Preconditions.checkArgument(constraints != null, "constraints was null but expected not null");

		Preconditions.checkArgument(type != null, "type was null but expected not null");
		
		//Vérifier qu'il n'y a pas 2(ou +) contraintes du même type, exemple : [NOTYOU, NOTALLY, NOTYOU]
		ArrayList<TargetConstraint> alc = new ArrayList<>();
		for(TargetConstraint c : constraints)
		{
			if(alc.contains(c))
			{
				throw new IllegalArgumentException("constraints contains 2(or more) same constraints but"
						+ " can't contains 2(or more) same constraint, exemple : [NOTYOU, NOTALLY, NOTYOU]");
			}
			alc.add(c);
		}
		
		this.constraints = constraints;
		this.type = type;
	}
	
	public TargetConstraint[] getConstraints(){
		return constraints;
	}
	
	public TargetType getType(){
		return type;
	}
}
