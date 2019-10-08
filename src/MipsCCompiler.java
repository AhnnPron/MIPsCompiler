
public class MipsCCompiler 
{
	private DataMemory theDataMemory;
	private RegisterCollection theRegisterCollection;
	private SourceReader theSource;
	private String filename;
	
	public MipsCCompiler(String filename)
	{
		this.filename = filename;
		this.theSource = new SourceReader(filename);
		this.theDataMemory = new DataMemory(200000);
		this.theRegisterCollection = new RegisterCollection(16); // $zero to $t15
	
		
		/*
		    int x; --> addi $t0, $zero, 5000
			x = 7; --> addi $1, $zero, 7
					   sw $t1, 0($t0)
		 */
		
		String instruction; 
		String output;
		while(true)
		{
			instruction = this.theSource.getNextLine();
			if(instruction.contentEquals("End of File"))
			{
				break; // we're done!
			}
			else
			{
				// process this instruction
				output = "";
				String[] parts = instruction.split(" "); // count the buckets except the spaces
				if(parts[0].trim().contentEquals("int")) // if the first instruction is an int
				{
					String varName = parts[1].trim(); // grabbing the variable name x
					varName = varName.substring(0, varName.length()-1); // grab everything but the semicolon at the end
					
					output += "addi " + this.theRegisterCollection.getNextAvailableRegisterName(varName) // letting the register know it is associated with the varName 
					+ ", $zero, " + this.theDataMemory.getAddressForNewMemory();
				}
				else
				{
					parts = instruction.split("="); // count the buckets except the equals sign
					String varValue = parts[1].trim(); // x = 7 --> grabbing the 7
					varValue = varValue.substring(0, varValue.length()-1); // grab everything but the semicolon at the end --> varValue.length()-1
					
					String tempRegisterName =  this.theRegisterCollection.getNextAvailableRegisterName("__tempImmediate__"); // $t1
					
					String oldRegisterName = this.theRegisterCollection.getRegisterNameByVarName(parts[0].trim()); // $t0
					
					output += "addi " + tempRegisterName + ", $zero, " +  varValue + "\n" 
					+ "sw " + tempRegisterName + ", 0(" + oldRegisterName + ")"; 
				}
				System.out.println(output); 
			}
		}
	}
}
