import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.PorterStemmerTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.tokenize.TokenSampleStream;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class LibTokDemo {
	
	private static String paragraph = "Let's pause, \nand then reflect.";

	public static void main(String[] args) {
	
		System.out.println("Токенизация при помощи библиотеки OpenNLP");
		
		System.out.println("\nИспользование класса SimpleTokenizer");
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
		String tokens[] = simpleTokenizer.tokenize(paragraph);
		for (String token: tokens) {
			System.out.println(token);
		}
		System.out.println("\n\n");

		System.out.println("Токенизация при помощи WhitespaceTokenizer");
		tokens = WhitespaceTokenizer.INSTANCE.tokenize(paragraph);
		for (String token: tokens) {
                        System.out.println(token);
                }
                System.out.println("\n\n");


		System.out.println("Использование класса TokenizerME");
		try {
			InputStream modelIn = new FileInputStream(new File(getModelDir(), "opennlp-en-ud-ewt-tokens-1.2-2.5.0.bin" ));

			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			tokens = tokenizer.tokenize(paragraph);
			for (String token: tokens) {
                        System.out.println(token);
	                }
                System.out.println("\n\n");
		} catch (IOException ex){
		       ex.printStackTrace();	
		}

		System.out.println("\nИспользование класса TokenizerME на русском");
                try {
                        InputStream modelIn1 = new FileInputStream(new File(getModelDir(), "opennlp-ru-ud-gsd-tokens-1.2-2.5.0.bin" ));

                        TokenizerModel model1 = new TokenizerModel(modelIn1);
                        Tokenizer tokenizer1 = new TokenizerME(model1);
			String paragraph1 = "Проверка. Какая-то. Фраза на русском языке. В 2024 году была Олимпиада в Париже! Диапозон положительных оценок составляет 3-5";
                        String tokens1[] = tokenizer1.tokenize(paragraph1);
                        for (String token: tokens1) {
                        	System.out.println(token);
                        }
                System.out.println("\n\n");
               
	       	} catch (IOException ex){
                       ex.printStackTrace();
                }


		System.out.println("Использование токенайзера из библиотеки Stanford");
		CoreLabelTokenFactory ctf = new CoreLabelTokenFactory();
		PTBTokenizer ptb = new PTBTokenizer(new StringReader(paragraph), ctf, "invertible=true");
		while (ptb.hasNext()){
			CoreLabel cl = (CoreLabel) ptb.next();
			System.out.println(cl.originalText() + " (" + cl.beginPosition() + "-" + cl.endPosition()+ " )");

		}

		System.out.println("\n\n");


		System.out.println("Токенизация при помощи класса DocumentPreprocessor (Stanford)");
		Reader reader = new StringReader(paragraph);
		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(reader);

		Iterator <List<HasWord>> it = documentPreprocessor.iterator();
		while (it.hasNext()) {
			List<HasWord> sentence = it.next();
			for (HasWord token2 : sentence){
				System.out.println(token2);
			}
		
		}

	}

	public static File getModelDir() {
		return new File("/home/share/4.142.2.23/openNLPModels");

	}
} 
