
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
			x = 7; --> addi $t1, $zero, 7
					   sw $t1, 0($t0)
		 */
		
		/* 
		 	z = x + y; --> lw $t3, 0($t0) 		addi $t0, $zero, 5000
		 				   lw $t4, 0($t1)		addi $t1, $zero, 7
		 				   add $t5, $t3, $t4	sw $t1, 0($t0)
		 				   sw #t5, 0($t2)		addi $t2, $zero 5004
		 				   						addi $t3, $zero, 3
		 				   						sw $t3, 0($t2)
		 				   						
		 				   						lw $t4, 0($t0)
		 				   						lw $t5, 0($t1)
		 				   						add $t6, $t4, $t5
		 				   						sw $t6, 0($t3)
		 */
		
		String instruction; 
		String output;
		while(true)
		{
			instruction = this.theSource.getNextLine();
			if(instruction.equals("End of File"))
			{
				break; // we're done!
			}
			else
			{
				// process this instruction
				output = "";
				String[] parts = instruction.split(" "); // count the buckets except the spaces
				if(parts[0].trim().equals("int")) // if the first instruction is an int
				{
					String varName = parts[1].trim(); // grabbing the variable name x
					varName = varName.substring(0, varName.length()-1); // grab everything but the semicolon at the end
	
					
					output += "addi " + this.theRegisterCollection.getNextAvailableRegisterName(varName) // letting the register know it is associated with the varName 
					+ ", $zero, " + this.theDataMemory.getAddressForNewMemory();
				}
				
				else if (parts[1].trim().equals("="))
				{
					parts = instruction.split("="); // count the buckets except the equals sign
					String tempRegisterName = this.theRegisterCollection.getNextAvailableRegisterName("__tempImmediate__"); // $t1
					String oldRegisterName = this.theRegisterCollection.getRegisterNameByVarName(parts[0].trim()); // $t0
					
					String varValue = parts[1].trim(); // x = 7 --> grabbing the 7
					varValue = varValue.substring(0, varValue.length()-1); // grab everything but the semicolon at the end 
					
					output += "addi " + tempRegisterName + ", $zero, " +  varValue + "\n" 
					+ "sw " + tempRegisterName + ", 0(" + oldRegisterName + ")"; 
				}
					
				else if (parts.length > 2 && parts[3].trim().equals("+"));
				{
					parts = instruction.split("="); // count the buckets except the equals sign
					String newRegisterX = this.theRegisterCollection.getRegisterNameByVarName(parts[1].trim()); // accessing x
					String newRegisterY = this.theRegisterCollection.getRegisterNameByVarName(parts[3].trim()); // accessing y
					newRegisterY = newRegisterY.substring(0, newRegisterY.length()-1);
					String newRegisterZ = this.theRegisterCollection.getNextAvailableRegisterName("__tempZ__"); // accessing z
								
					output += "lw " + newRegisterX + ", 0(" + "oldRegisterName" + ")" + "\n"
					+ "lw " + newRegisterY + ", 0(" + "tempRegisterName" + ")" + "\n"
					+ "add " + newRegisterZ + ", " + newRegisterX + ", " + newRegisterY + "\n" 
					+ "sw " + newRegisterZ + ", 0(" + this.theRegisterCollection.getNextAvailableRegisterName("__tempStore__") + ")";
					
				}
				
				System.out.println(output); 
			}
		}
	}
}
