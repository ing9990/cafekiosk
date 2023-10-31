package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.sellingStatus in ?1")
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

    @Query("select p from Product p where p.productNumber in ?1")
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
