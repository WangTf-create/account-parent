package com.citi.mvnbook.account.persist.service.impl;

import com.citi.mvnbook.account.persist.exceptions.AccountPersistException;
import com.citi.mvnbook.account.persist.model.Account;
import com.citi.mvnbook.account.persist.service.AccountPersistService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.citi.mvnbook.account.persist.constant.PersistConstants.*;

/**
 * @author wangtongfa
 * @date 2022/11/5 22:05
 */
@Service
public class AccountPersistServiceImpl implements AccountPersistService {


    @Value("${persist.file}")
    private String path;

    private final SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException {

        File dataFile = new File(path);
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();

            Document document = DocumentFactory.getInstance().createDocument();

            Element rootElement = document.addElement(ACCOUNT_ROOT);
            rootElement.addElement(ACCOUNTS_ELEMENT);
            writeDocument(document);
        }
        try {
            return reader.read(new File(path));
        } catch (DocumentException e) {
            throw new AccountPersistException("Unable to read persist data.xml", e);
        }

    }

    private void writeDocument(Document document) throws AccountPersistException {
        XMLWriter writer = null;

        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {

            writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(document);
        } catch (IOException e) {
            throw new AccountPersistException("Unable to write persist data xml", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new AccountPersistException("Unable to close persist data xml", e);
            }
        }
    }

    private Account buildAccount(Element element) {
        Account account = new Account();
        account.setId(element.elementText(ACCOUNT_ELEMENT_ID));
        account.setName(element.elementText(ACCOUNT_ELEMENT_NAME));
        account.setPassword(element.elementText(ACCOUNT_ELEMENT_PASSWORD));
        account.setEmail(element.elementText(ACCOUNT_ELEMENT_EMAIL));
        account.setActivated(BooleanUtils.toBoolean(element.elementText(ACCOUNT_ELEMENT_ACTIVATED)));
        return account;
    }

    @Override
    public Account createAccount(Account account) throws AccountPersistException {
        Document document = DocumentFactory.getInstance().createDocument();
        Element rootEle = document.addElement(ACCOUNT_ROOT);
        Element accountsEle = rootEle.addElement(ACCOUNTS_ELEMENT);
        Element accountEle = accountsEle.addElement(ACCOUNT_ELEMENT);
        accountEle.addElement(ACCOUNT_ELEMENT_ID).setText(account.getId());
        accountEle.addElement(ACCOUNT_ELEMENT_NAME).setText(account.getName());
        accountEle.addElement(ACCOUNT_ELEMENT_PASSWORD).setText(account.getPassword());
        accountEle.addElement(ACCOUNT_ELEMENT_EMAIL).setText(account.getEmail());
        accountEle.addElement(ACCOUNT_ELEMENT_ACTIVATED).setText(String.valueOf(account.isActivated()));
        writeDocument(document);
        return account;
    }

    @Override
    public Account readAccount(String id) throws AccountPersistException {
        Document document = readDocument();
        Element accountsEle = document.getRootElement().element(ACCOUNTS_ELEMENT);
        for (Element accountEle : (List<Element>)accountsEle.elements()) {
            if (StringUtils.equals(accountEle.elementText(ACCOUNT_ELEMENT_ID),id)){
                return buildAccount(accountEle);
            }
        }
        return null;
    }

    @Override
    public Account updateAccount(Account account) throws AccountPersistException {
        return null;
    }

    @Override
    public Account deleteAccount(String id) throws AccountPersistException {
        return null;
    }
}
