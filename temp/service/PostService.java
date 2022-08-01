package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.entity.Board;
import project.entity.User;
import project.repository.BoardRepository;
import project.repository.UserRepository;
import project.web.dto.PostCreateReqDto;
import project.web.dto.PostDto;
import project.web.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<PostDto> findAll() {
        List<PostDto> result = new ArrayList<>();

        log.info("게시글 목록을 조회합니다.");
        for (Board board : boardRepository.findAll()) {
            User user = board.getUser();

            result.add(PostDto.builder()
                            .postNo(board.getPostNo())
                            .postTitle(board.getPostTitle())
                            .postContent(board.getPostContent())
                            .postCreateDt(board.getPostCreateDt())
                            .postModifyDt(board.getPostModifyDt())
                            .userDto(new UserDto(user.getUsername(), user.getName(), user.getUserClass()))
                    .build());
        }

        return result;
    }

    public void create(PostCreateReqDto dto, String username) {
        log.info("{} 님이 게시물을 작성하였습니다.", username);

        boardRepository.save(Board.builder()
                .postTitle(dto.getPostTitle())
                .postContent(dto.getPostContent())
                .postCreateDt(LocalDateTime.now())
                .user(userRepository.findByUsername(username))
                .build());
    }

    public PostDto findById(Long id) {
        log.info("{} 번 게시글을 조회합니다.", id);

        Optional<Board> findBoard = boardRepository.findById(id);

        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            User user = board.getUser();

            return PostDto.builder()
                    .postNo(board.getPostNo())
                    .postTitle(board.getPostTitle())
                    .postContent(board.getPostContent())
                    .postCreateDt(board.getPostCreateDt())
                    .postModifyDt(board.getPostModifyDt())
                    .userDto(new UserDto(user.getUsername(), user.getName(), user.getUserClass()))
                    .build();
        }

        return null;
    }
}