//package com.avenuecode.udemy.account.manager.bootstrap;
//
//import com.avenuecode.udemy.account.manager.repository.AccountRepository;
//import com.avenuecode.udemy.account.manager.repository.ScheduleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BootStrapData implements CommandLineRunner {
//
//    private final AccountRepository accountRepository;
//    private final ScheduleRepository scheduleRepository;
//
//    public BootStrapData(final AccountRepository accountRepository, final ScheduleRepository scheduleRepository) {
//        this.accountRepository = accountRepository;
//        this.scheduleRepository = scheduleRepository;
//    }
//
//
//    @Override
//    public void run(final String... args) throws Exception {
//
////        EmailSender teste = new EmailSender();
////        teste.ok();
//
////        String sDate1="31/12/1998";
////        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
////
////        Account conta1 = new Account("test@avenuecode.com","test123");
////
////        Schedule schedule1 = new Schedule(conta1, new Date(), "michel@avenuecode.com",
////                "pleno", "Java", "16", "Technical skills");
////        Schedule schedule2 = new Schedule(conta1, date1, "michel@avenuecode.com",
////                "pleno", "Java", "16", "Technical skills");
////
////        conta1.getSchedules().add(schedule1);
////        conta1.getSchedules().add(schedule2);
////        accountRepository.save(conta1);
////        scheduleRepository.save(schedule1);
////        scheduleRepository.save(schedule2);
////
////        System.out.println("Numero de contas: " + accountRepository.count());
////        System.out.println("Quantidade de agendamentos: " + scheduleRepository.count());
////        System.out.println("Conta:" + conta1.toString());
////        System.out.println("Agendamento:" + schedule1.toString());
////        System.out.println("Usuario agendado: " + schedule1.getScheduledFor());
//    }
//}
