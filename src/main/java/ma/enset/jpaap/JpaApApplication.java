package ma.enset.jpaap;

import ma.enset.jpaap.entities.Patient;
import ma.enset.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(JpaApApplication.class, args);
    }
     @Override
     public void run(String... args) throws Exception{
        for (int i=0;i<100;i++) {
            patientRepository.save(new Patient(null,"asmaa",new Date(),Math.random()>0.5?true:false,(int)(Math.random()*100)));
        }

        Page<Patient> patients = patientRepository.findAll(PageRequest.of(1,10));//retourne la liste des produits
         System.out.println("Total pages :"+patients.getTotalPages());
         System.out.println("Total elements :"+patients.getTotalElements());
         System.out.println("Num page :"+patients.getNumber());
         List<Patient> content =patients.getContent(); // contenu
         List<Patient> byMalade = patientRepository.findByMalade(true);
         List<Patient> patientList=patientRepository.chercherPatients("%a%",50);
         byMalade.forEach(p->{
                System.out.println("--------------");
                System.out.println(p.getId());
                System.out.println(p.getNom());
                System.out.println(p.getScore());
                System.out.println(p.getDateNaissance());
                System.out.println(p.getMalade());
            });
         System.out.println("************");
         Patient patient=patientRepository.findById(3L).orElse(null);
         if (patient!=null){
             System.out.println(patient.getNom());
             System.out.println(patient.getMalade());
         }
         patient.setScore(890);
         patientRepository.save(patient);
         patientRepository.deleteById(1L);
     }
}
