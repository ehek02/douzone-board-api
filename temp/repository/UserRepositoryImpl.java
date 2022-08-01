//package project.repository;
//
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//import project.entity.User;
//
//@Repository
//public class UserRepositoryImpl extends QuerydslRepositorySupport implements CustomUserRepository{
//
//    public UserRepositoryImpl() {
//        super(User.class);
//    }
//
//
//    @Override
//    public String findRefreshTokenByUsername(String username) {
//
//        QUser user = QUser.user;
//
//        return from(user)
//            .select(user.refreshToken)
//            .where(user.username.eq(username))
//            .fetchOne();
//    }
//}
