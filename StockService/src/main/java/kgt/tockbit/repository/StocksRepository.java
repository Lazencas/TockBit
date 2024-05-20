package kgt.tockbit.repository;

import kgt.tockbit.domain.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StocksRepository extends JpaRepository<Stocks, String> {
}
