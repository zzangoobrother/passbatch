package com.fastcampus.pass.repository.packaze;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PackageEntityRepositoryTest {

    @Autowired
    private PackageEntityRepository packageEntityRepository;

    @Test
    void test_save() {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("바디 챌린지 PT 12주");
        packageEntity.setPeriod(84);

        packageEntityRepository.save(packageEntity);

        assertNotNull(packageEntity.getPackageSeq());
    }

    @Test
    void test_findByCreatedAtAfter() {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        PackageEntity packageEntity0 = new PackageEntity();
        packageEntity0.setPackageName("학생 전용 3개월");
        packageEntity0.setPeriod(90);
        packageEntityRepository.save(packageEntity0);

        PackageEntity packageEntity1 = new PackageEntity();
        packageEntity1.setPackageName("학생 전용 6개월");
        packageEntity1.setPeriod(180);
        packageEntityRepository.save(packageEntity1);

        final List<PackageEntity> packageEntities = packageEntityRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0, 1, Sort.by("packageSeq").descending()));

        assertEquals(1, packageEntities.size());
        assertEquals(packageEntity1.getPackageSeq(), packageEntities.get(0).getPackageSeq());
    }

    @Test
    void test_updateCountAndPeriod() {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("바디프로필 이벤트 4개월");
        packageEntity.setPeriod(90);
        packageEntityRepository.save(packageEntity);

        int updateCount = packageEntityRepository.updateCountAndPeriod(packageEntity.getPackageSeq(), 30, 120);

        final PackageEntity updatePackageEntity = packageEntityRepository.findById(packageEntity.getPackageSeq()).get();

        assertEquals(1, updateCount);
        assertEquals(30, updatePackageEntity.getCount());
        assertEquals(120, updatePackageEntity.getPeriod());
    }

    @Test
    void test_delete() {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("제거할 이용권");
        packageEntity.setCount(1);
        PackageEntity newPackageEntity = packageEntityRepository.save(packageEntity);

        packageEntityRepository.deleteById(newPackageEntity.getPackageSeq());

        assertTrue(packageEntityRepository.findById(newPackageEntity.getPackageSeq()).isEmpty());
    }
}
