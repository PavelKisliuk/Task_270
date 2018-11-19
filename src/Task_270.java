import javax.xml.stream.events.Characters;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task_270 {
	public static void main(String[] args) {
		VariableName test = new VariableName();
		try(Formatter output = new Formatter("OUTPUT.TXT")) {
			output.format(test.toString());
		}catch (FileNotFoundException | FormatterClosedException e) {
			e.printStackTrace();
		}
	}
}

class VariableName {
	private String name;
	/*public*/ private VariableName(final String path)
	{

		try(final Scanner input = new Scanner(Paths.get(path))) {
			//-----------------------------------------------------------------------------
			if(input.hasNext()) {
				String nameFromFile = input.nextLine();
				int test = this.whatsString(nameFromFile);
				if(test == 0) {
					this.name = this.toJava(nameFromFile);
				}
				else if(test == 1) {
					this.name = this.toCPlusPlus(nameFromFile);
				}
				else {
					this.name = "Error!";
				}
			}
			//-----------------------------------------------------------------------------
			else {
				throw new IOException("File is empty!");
			}

			//-----------------------------------------------------------------------------
		} catch (IOException | NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/*public*/ VariableName()
	{
		this("INPUT.TXT");
	}

	private int whatsString(String s)
	{
		if(s.contains("_")) {
			Pattern expression = Pattern.compile("__+|\\p{javaUpperCase}");
			Matcher matcher = expression.matcher(s);
			if((s.charAt(0) == '_') || (s.charAt(s.length() - 1) == '_') || matcher.find()) {
				return -1;
			}
			else {
				return 0;
			}
		}
		else {
			if(Character.isUpperCase(s.charAt(0))) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}

	private String toCPlusPlus(String s) {
		String[] sArr = s.split("\\p{javaUpperCase}+");
		String[] sArr2 = s.split("\\p{javaLowerCase}+");

		if(sArr2.length == 0) {
			return s;
		}

		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < sArr2.length; i++) {
			if(sArr2[i].length() == 1) {
				stringBuilder.append(sArr2[i].toLowerCase());
				if(i < sArr.length) {
					stringBuilder.append(sArr[i]);
				}
			}
			else {
				String tempString = sArr2[i].toLowerCase();
				for(int j = 0; j < tempString.length(); j++) {
					stringBuilder.append(tempString.charAt(j));
					if(j != tempString.length() - 1) {
						stringBuilder.append('_');
					}
				}
				if(i < sArr.length) {
					stringBuilder.append(sArr[i]);
				}
			}


			stringBuilder.append('_');
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);

		return  stringBuilder.toString();
	}

	private String toJava(String s) {
		String[] sArr = s.split("_");
		StringBuilder stringBuilder = new StringBuilder();
		for(String tempString : sArr) {
			StringBuilder tempStringBuilder = new StringBuilder(tempString);
			Character ch = Character.toUpperCase(tempString.charAt(0));
			tempStringBuilder.replace(0, 1, ch.toString());
			stringBuilder.append(tempStringBuilder);
		}
		Character ch = Character.toLowerCase(stringBuilder.charAt(0));
		stringBuilder.replace(0, 1, ch.toString());

		return stringBuilder.toString();
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}