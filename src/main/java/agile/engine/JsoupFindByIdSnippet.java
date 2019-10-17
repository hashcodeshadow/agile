package agile.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class JsoupFindByIdSnippet {

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindByIdSnippet.class);

    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case

        String resourcePath = "./samples/startbootstrap-freelancer-gh-pages-cut.html";
        String targetElementId = "sendMessageButton"; //make-everything-ok-button sendMessageButton //String.valueOf(args[1]);
        String pathDiffCase = "./samples/diff-startbootstrap-freelancer-gh-pages-cut.html"; //input_other_sample_file_path String.valueOf(args[2])

        if(null!=args&&args.length==3){
            resourcePath  = String.valueOf(args[0]);
            targetElementId  = String.valueOf(args[1]);
            pathDiffCase  = String.valueOf(args[2]);
        }

        Optional<Element> buttonOpt = findElementById(new File(resourcePath), targetElementId);

        List<String> listResult=new ArrayList<String>();

        if(buttonOpt.isPresent()) {
            Optional<String> stringifiedAttributesOpt = buttonOpt.map(button ->
                    button.attributes().asList().stream()
                            .map(attr -> attr.getKey() + " = " + attr.getValue())
                            .collect(Collectors.joining(", "))
            );
            stringifiedAttributesOpt.ifPresent(attrs -> LOGGER.info("Target element attrs: [{}]", attrs));

            String element = stringifiedAttributesOpt.get();
            //find into diff

            StringTokenizer tokens = new StringTokenizer(element,",");
            while(tokens.hasMoreTokens()){
                String newToken = tokens.nextToken();
                log.info("New Token: {}",newToken);

                StringTokenizer valueToken = new StringTokenizer(element,"= ");
                int i=1;
                while(valueToken.hasMoreTokens()){
                    if(i%2==0) {
                        String val = valueToken.nextToken();
                        log.info("Value:{}", val);

                        Optional<Element> similarElement = findElementById(new File(pathDiffCase), element);

                        if (similarElement.isPresent()) {
                            Optional<String> diffEleOpt = buttonOpt.map(but ->
                                    but.attributes().asList().stream()
                                            .map(att -> att.getKey() + " = " + att.getValue())
                                            .collect(Collectors.joining(", "))
                            );
                            diffEleOpt.ifPresent(attrs -> log.info("Diff element attrs: [{}]", attrs));

                            if (diffEleOpt.isPresent()) {
                                listResult.add(diffEleOpt.get());
                            }
                        }

                    }
                }

                listResult.forEach(v -> log.info("Final Result:{}",v));
            }

        }else{
            log.info("Nothing to show");
        }


    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

}