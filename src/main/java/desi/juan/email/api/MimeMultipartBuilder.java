package desi.juan.email.api;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MimeMultipartBuilder {
    public static final String PLAIN_TEXT_TYPE = "text/text;charset=UTF-8";
    public static final String PLAIN_HTML_TYPE = "text/html;charset=UTF-8";

    private MimeBodyPart textPart;
    private List<MimeBodyPart> imageParts = new ArrayList<MimeBodyPart>();
    private List<MimeBodyPart> attachParts = new ArrayList<MimeBodyPart>();

    public static MimeMultipartBuilder create() {
        return new MimeMultipartBuilder();
    }

    public MimeMultipart build() throws MessagingException {
        MimeMultipart mp = new MimeMultipart();
        if (null != textPart) {
            mp.addBodyPart(textPart);
        }
        for (MimeBodyPart imagePart : imageParts) {
            mp.addBodyPart(imagePart);
        }

        for (MimeBodyPart attachPart : attachParts) {
            mp.addBodyPart(attachPart);
        }

        return mp;
    }

    public MimeMultipartBuilder plainText(String content) throws MessagingException {
        if (null != textPart) {
            throw new IllegalStateException("plain init twice!");
        }

        textPart = new MimeBodyPart();
        textPart.setContent(content, PLAIN_TEXT_TYPE);
        return this;
    }

    public MimeMultipartBuilder plainHtml(String content) throws MessagingException {
        textPart = new MimeBodyPart();
        textPart.setContent(content, PLAIN_HTML_TYPE);
        return this;
    }

    public MimeMultipartBuilder withImages(String... images) throws MessagingException {
        File[] files = convert(images);
        return withImages(files);
    }

    public MimeMultipartBuilder withImages(File... images) throws MessagingException {
        for (File file : images) {
            String fileName = file.getName();
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDataHandler(new DataHandler(new FileDataSource(file)));
            imagePart.setContentID(fileName);

            imageParts.add(imagePart);
        }

        return this;
    }

    /**
     * @param map key:contentid, value:image file
     * @return
     */
    public MimeMultipartBuilder withImages(Map<String, File> map) throws MessagingException {
        for (Map.Entry<String, File> entry : map.entrySet()) {
            String contentId = entry.getKey();
            File file = entry.getValue();
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDataHandler(new DataHandler(new FileDataSource(file)));
            imagePart.setContentID(contentId);

            imageParts.add(imagePart);
        }

        return this;
    }

    public MimeMultipartBuilder withAttach(String... attaches) throws MessagingException, UnsupportedEncodingException {
        File[] files = convert(attaches);
        return withAttach(files);
    }

    public MimeMultipartBuilder withAttach(File... attachs) throws MessagingException, UnsupportedEncodingException {
        for (File file : attachs) {
            String fileName = file.getName();
            MimeBodyPart attachPart = new MimeBodyPart();

            DataHandler dh = new DataHandler(new FileDataSource(file));
            attachPart.setDataHandler(dh);
            attachPart.setFileName(MimeUtility.encodeText(fileName));
            attachParts.add(attachPart);
        }

        return this;
    }

    public File[] convert(String... attachs) {
        File[] files = new File[attachs.length];

        for (int i = 0; i < attachs.length; i++) {
            String attach = attachs[i];
            files[i] = new File(attach);
        }

        return files;
    }
}