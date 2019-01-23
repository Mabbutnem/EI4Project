package target;

import java.util.ArrayList;

public class Target
{
	private TargetConstraint[] constraints;
	private TargetType type;
	
	public Target(TargetConstraint[] constraints, TargetType type)
	{
		if(constraints == null) {throw new IllegalArgumentException("constraints ne peut pas être null");}
		
		if(type == null) {throw new IllegalArgumentException("type ne peut pas être null");}
		
		//Vérifier qu'il n'y a pas 2(ou +) contraintes du même type, exemple : [NOTYOU, NOTALLY, NOTYOU]
		ArrayList<TargetConstraint> alc = new ArrayList<>();
		for(TargetConstraint c : constraints)
		{
			if(alc.contains(c))
			{
				throw new IllegalArgumentException("constraints ne peut pas contenir 2(ou +) contraintes du"
						+ " même type, exemple : [NOTYOU, NOTALLY, NOTYOU]");
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
