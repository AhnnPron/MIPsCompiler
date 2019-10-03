
public class RegisterCollection 
{
	private Register[] theRegisters;
	
	public RegisterCollection(int numOfStandardRegisters)
	{
		this.theRegisters = new Register[numOfStandardRegisters + 1];
		this.theRegisters[0] = new Register("$zero"); // $zero
		for(int i = 1; i < this.theRegisters.length; i++)
		{
			this.theRegisters[i] = new Register("$t" + (i-1)); // $t1, $t2, $t3, etc
		}
	}
	
	public String getNextAvailableRegisterName()
	{
		for(int i = 1; i < this.theRegisters.length; i++)
		{
			if(!this.theRegisters[i].isInUse()) // find a register that's not in use
			{
				this.theRegisters[i].setInUse(true); // once we find one that's available, we'll use it and then it will be inUse
				return this.theRegisters[i].getName(); // return the name of that corresponding register
			}
		}
		throw new RuntimeException("You're out of available registers"); 
	}
	
	
	public int getValueByRegisterName(String name)
	{
		for(Register r : this.theRegisters)
		{
			if(r.getName().equals(name)) // if the register I'm looking at is the register I'm searching for 
			{
				return r.getValue();
			}
		}
		throw new RuntimeException("The register you're looking for cannot be found");
	}
	
	public String getRegisterbyValue(int num)
	{
		for(Register r : this.theRegisters)
		{
			if(r.getValue() == num)
			{
				return r.getName();
			}
		}
		throw new RuntimeException("The register you're looking for cannot be found");
	}
	
	
	/*public String getRegisterLastUsed()
	{
		for(int i = 1; i < this.theRegisters.length; i++)
		{
			if(!this.theRegisters[i].isInUse()) // find a register that's not in use
			}
				return this.theRegisters[i-1].getName(); 
			}
		}
		throw new RuntimeException("Last register not found"); 
	}*/
}
