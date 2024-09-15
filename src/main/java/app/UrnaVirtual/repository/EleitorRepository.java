package app.UrnaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.UrnaVirtual.entity.Eleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

}
