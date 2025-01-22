package com.sh.trippy.domain.reply.domain.repository;

import com.sh.trippy.domain.reply.domain.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
