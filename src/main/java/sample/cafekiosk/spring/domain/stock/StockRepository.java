package sample.cafekiosk.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select s from Stock s where s.productNumber in ?1")
    List<Stock> findAllByProductNumberIn(List<String> productNumbers);
}
