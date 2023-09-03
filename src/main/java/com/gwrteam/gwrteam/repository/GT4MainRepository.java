package com.gwrteam.gwrteam.repository;

        import com.gwrteam.gwrteam.model.GT4TableMain;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface GT4MainRepository extends JpaRepository<GT4TableMain, Long> {
    @Override
    List<GT4TableMain> findAll();
}