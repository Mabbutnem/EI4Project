package target;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Target
{
	private TargetConstraint[] constraints;
	private TargetType type;
	
	public Target()
	{
		super();
	}
	
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
						+ " can't contains 2(or more) same constraint, example : [NOTYOU, NOTALLY, NOTYOU]");
			}
			alc.add(c);
		}
		
		//Vérifier que si type = YOU ou MORE il n'y a pas de contrainte
		if(type == TargetType.YOU || type == TargetType.MORE)
		{
			Preconditions.checkArgument(constraints.length == 0, "constraints must be empty if the target type is YOU or MORE");
		}
		
		//Vérifier que que les contraintes NOTALLY et NOTENEMY ne sont pas toutes les deux présentes en même temps
		Preconditions.checkArgument(
				!alc.contains(TargetConstraint.NOTALLY) || !alc.contains(TargetConstraint.NOTENEMY),
				"constraints can't contains NOTALLY and NOTENEMY constraints at the same time");
		
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
