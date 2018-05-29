package org.spe.service;

import org.spe.domain.Factory;
import org.spe.domain.Mail;
import org.spe.domain.MailIdService;
import org.spe.domain.MailService;
import org.spe.domain.Repository;
import org.spe.domain.Worker;
import org.spe.infrastructure.ConnectionFactoryJDBCImpl;
import org.spe.infrastructure.MailIdServiceJDBCImpl;
import org.spe.infrastructure.MailRepositoryJDBCImpl;
import org.spe.infrastructure.MailServiceJavaxImpl;

import java.sql.Connection;

/**
 * @author Maxim
 * @version 1.0
 * @since <pre>05.10.2014</pre>
 */
public class SendMailService {

    public static final String FROM_ADDRESS = "sender@here.com";

    private Factory<Connection> factory;

    public void init() {
        factory = new ConnectionFactoryJDBCImpl();
    }

    public synchronized String send(String message) {
        MailService mailService = new MailServiceJavaxImpl(FROM_ADDRESS);
        Repository<Mail> repository = new MailRepositoryJDBCImpl(factory);
        MailIdService idService = new MailIdServiceJDBCImpl(factory);
        Worker worker = new Worker(mailService, repository, idService);
        return worker.run(message);
    }


}
