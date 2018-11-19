import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task_270 {
	public static void main(String[] args) {
		final String outputPath = "OUTPUT.TXT";
		final VariableName test = new VariableName();
		try(final BufferedWriter output = Files.newBufferedWriter(Paths.get(outputPath))) {
			output.write(test.toString());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//-----------------------------------------------------------------------------
/*public*/ class VariableName {
	//-----------------------------------------------------------------------------fields
	private String name;
	//-----------------------------------------------------------------------------constructors
	/*public*/ private VariableName(final String path)
	{
		final int cPlusPlusString = 0;
		final int javaString = 1;
		try(final BufferedReader input = Files.newBufferedReader(Paths.get(path))) {
			//-----------------------------------------------------------------------------
			if(input.ready()) {
				//-----------------------------------------------------------------------------
				final String nameFromFile = input.readLine();  //read data from file
				//-----------------------------------------------------------------------------
				final int whatsString = this.whatsString(nameFromFile);
				if(cPlusPlusString == whatsString) {
					this.name = this.toJava(nameFromFile);
				}
				else if(javaString == whatsString) {
					this.name = this.toCPlusPlus(nameFromFile);
				}
				else {
					this.name = "Error!";
				}
				//-----------------------------------------------------------------------------
			}
			//-----------------------------------------------------------------------------
			else {
				throw new IOException("File is empty!");
			}
			//-----------------------------------------------------------------------------
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public*/ VariableName()
	{
		this("INPUT.TXT");
	}
	//-----------------------------------------------------------------------------methods for constructors
	private int whatsString(final String s)
	{
		final int cPlusPlusString = 0;
		final int javaString = 1;
		final int errorString = -1;
		//-----------------------------------------------------------------------------
		if(s.contains("_")) {
			//-----------------------------------------------------------------------------
			Pattern expression = Pattern.compile("__+|\\p{javaUpperCase}");
			Matcher matcher = expression.matcher(s);
			if((s.charAt(0) == '_') || (s.charAt(s.length() - 1) == '_') || matcher.find()) {
				return errorString;
			}
			//-----------------------------------------------------------------------------
			else {
				return cPlusPlusString;
			}
		}
		//-----------------------------------------------------------------------------
		else {
			if(Character.isUpperCase(s.charAt(0))) {
				return errorString;
			}
			else {
				return javaString;
			}
		}
		//-----------------------------------------------------------------------------
	}

	private String toCPlusPlus(final String oldName) {
		final String[] lowerCaseStrings = oldName.split("\\p{javaUpperCase}+");
		final String[] upperCaseStrings = oldName.split("\\p{javaLowerCase}+");
		if(upperCaseStrings.length == 0) {
			return oldName;
		}
		//-----------------------------------------------------------------------------
		StringBuilder convertedName = new StringBuilder();
		for(int i = 0; i < upperCaseStrings.length; i++) {
			//-----------------------------------------------------------------------------
			if(upperCaseStrings[i].length() == 1) {
				convertedName.append(upperCaseStrings[i].toLowerCase());
				if(i < lowerCaseStrings.length) { //newRGB - example
					convertedName.append(lowerCaseStrings[i]);
				}
			}
			//-----------------------------------------------------------------------------
			else { //newRGBString - example
				//-----------------------------------------------------------------------------
				String tempString = upperCaseStrings[i].toLowerCase();
				for(int j = 0; j < tempString.length(); j++) {
					//-----------------------------------------------------------------------------
					convertedName.append(tempString.charAt(j));
					if(j != tempString.length() - 1) {
						convertedName.append('_');
					}
					//-----------------------------------------------------------------------------
				}
				//-----------------------------------------------------------------------------
				if(i < lowerCaseStrings.length) {
					convertedName.append(lowerCaseStrings[i]);
				}
			}
			convertedName.append('_');
		}
		convertedName.deleteCharAt(convertedName.length() - 1);
		return  convertedName.toString();
	}

	private String toJava(final String oldName) {
		final String[] nameSubStrings = oldName.split("_");
		//-----------------------------------------------------------------------------
		StringBuilder convertedName = new StringBuilder();
		for(String tempString : nameSubStrings) {
			//-----------------------------------------------------------------------------
			StringBuilder tempStringBuilder = new StringBuilder(tempString);
			char firstSymbol = Character.toUpperCase(tempString.charAt(0));
			//-----------------------------------------------------------------------------
			tempStringBuilder.replace(0, 1, String.valueOf(firstSymbol));
			convertedName.append(tempStringBuilder);
			//-----------------------------------------------------------------------------
		}
		char firstSymbol = Character.toLowerCase(convertedName.charAt(0));
		convertedName.replace(0, 1, String.valueOf(firstSymbol));
		return convertedName.toString();
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@Override
	public String toString()
	{
		return this.name;
	}
}