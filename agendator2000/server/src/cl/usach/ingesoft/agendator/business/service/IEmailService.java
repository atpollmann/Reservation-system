package cl.usach.ingesoft.agendator.business.service;

public interface IEmailService {
    boolean sendMessage(String from, String to, String subject, String body);
}
