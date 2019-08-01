package pl.adamsiedlecki.CryptoMessenger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamsiedlecki.CryptoMessenger.entity.Message;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message,Long> {

    List<Message> getAllByRoomIs(String room);
}
