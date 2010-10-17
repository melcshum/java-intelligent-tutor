package itjava.model;

import itjava.data.BlankType;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class WordInfoStore {
	private List<String> _linesOfCode;
	private WordInfo _wordInfo;
	
	private WordInfoStore(List<String> linesOfCode) {
		_linesOfCode = linesOfCode;
		_wordInfo = new WordInfo();
	}

	private void createWordInfo(ASTNode node) {
		int index = 0;
		int lineNumber = 0;
		for (String currLine : _linesOfCode) {
			index += currLine.length();
			if (index > node.getStartPosition()) {
				System.out.println("Current line: " + currLine);
				System.out.println("Substring: " + currLine.substring(currLine.trim().indexOf(_wordInfo.wordToBeBlanked)));
				System.out.println("Index of word: " + currLine.indexOf(_wordInfo.wordToBeBlanked));
				_wordInfo.lineNumber = lineNumber + 1;
				_wordInfo.blankType = BlankType.Text;
				_wordInfo.columnNumber = currLine.trim().indexOf(_wordInfo.wordToBeBlanked);
				break;
			}
			lineNumber++;
		}
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode, Statement statement) {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = ((VariableDeclarationStatement)statement).getType().toString();
		System.out.println("VariableDeclaration pos: " + statement.getStartPosition());
		wordInfoStore.createWordInfo(statement);
		return wordInfoStore._wordInfo;
		
	}

	public static WordInfo createWordInfo(List<String> linesOfCode,
			ImportDeclaration importDeclaration) {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = importDeclaration.getName().getFullyQualifiedName();
		System.out.println("ImportDeclaration pos: " + importDeclaration.getStartPosition());
		wordInfoStore.createWordInfo(importDeclaration);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			Expression initializer) {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = initializer.toString();
		System.out.println("Initializer pos: " + initializer.getStartPosition());
		wordInfoStore.createWordInfo(initializer);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			SimpleName methodInvocation) {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = methodInvocation.getFullyQualifiedName();
		System.out.println("Initializer pos: " + methodInvocation.getStartPosition());
		wordInfoStore.createWordInfo(methodInvocation);
		return wordInfoStore._wordInfo;
	}
}
