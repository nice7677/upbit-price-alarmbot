package kr.springboot.upbit.repository;

import kr.springboot.upbit.model.CheckListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<CheckListModel, Long> {

    CheckListModel findFirstByMarketNameOrderByIdxDesc(String name);

}
