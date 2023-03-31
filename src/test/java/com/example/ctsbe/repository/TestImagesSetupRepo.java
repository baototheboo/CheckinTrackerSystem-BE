//package com.example.ctsbe.repository;
//
//import com.example.ctsbe.dto.image.ImageSetupDTO;
//import com.example.ctsbe.entity.ImagesSetup;
//import com.example.ctsbe.entity.Staff;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//import javax.persistence.EntityManager;
//
//import java.time.Instant;
//import java.time.ZoneId;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class TestImagesSetupRepo {
//
//    @Autowired
//    private ImageSetupRepository imageSetupRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    void testFindImageSetupByStaffId() {
//        // Create and persist test data
//        Staff staff = new Staff();
//        staff.setId(1);
//        staff.setFirstName("John");
//        staff.setSurname("Doe");
//        entityManager.persist(staff);
//
//        ImagesSetup imagesSetup1 = new ImagesSetup();
//        imagesSetup1.setId(1);
//        imagesSetup1.setImage("image1");
//        imagesSetup1.setTimeSetup(Instant.now());
//        imagesSetup1.setStaff(staff);
//        imagesSetup1.setStatus("status1");
//        entityManager.persist(imagesSetup1);
//
//        ImagesSetup imagesSetup2 = new ImagesSetup();
//        imagesSetup2.setId(2);
//        imagesSetup2.setImage("image2");
//        imagesSetup2.setTimeSetup(Instant.now());
//        imagesSetup2.setStaff(staff);
//        imagesSetup2.setStatus("status2");
//        entityManager.persist(imagesSetup2);
//
//        // Execute the query
//        Page<ImageSetupDTO> results = imageSetupRepository.findImageSetupByStaffId(1, PageRequest.of(0, 10, Sort.by("id").ascending()));
//
//        // Assert that the query returned the expected result
//        assertThat(results.getContent()).hasSize(2);
//        assertThat(results.getContent().get(0).getImagesSetupId()).isEqualTo(1);
//        assertThat(results.getContent().get(0).getImage()).isEqualTo("image1");
//        assertThat(results.getContent().get(0).getTimeSetup()).isEqualTo(imagesSetup1.getTimeSetup().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime());
//        assertThat(results.getContent().get(0).getStaffId()).isEqualTo(1);
//        assertThat(results.getContent().get(0).getStatus()).isEqualTo("status1");
//        assertThat(results.getContent().get(0).getFirstName()).isEqualTo("John");
//        assertThat(results.getContent().get(0).getSurname()).isEqualTo("Doe");
//    }
//}
