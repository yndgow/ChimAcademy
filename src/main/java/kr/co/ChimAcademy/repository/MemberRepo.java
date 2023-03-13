package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.MemberEntity;

public interface MemberRepo extends JpaRepository<MemberEntity, String>{

}
