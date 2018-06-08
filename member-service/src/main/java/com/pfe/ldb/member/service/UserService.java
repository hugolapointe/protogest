package com.pfe.ldb.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfe.ldb.core.protogest.user.User;
import com.pfe.ldb.entity.MemberEntity;
import com.pfe.ldb.entity.UserAuthoritiesEntity;
import com.pfe.ldb.entity.UserEntity;
import com.pfe.ldb.member.iservice.IUserService;
import com.pfe.ldb.member.mapper.MemberMapper;
import com.pfe.ldb.member.mapper.UserMapper;
import com.pfe.ldb.member.repository.MemberRepository;
import com.pfe.ldb.member.repository.RoleRepository;
import com.pfe.ldb.member.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
   
    private final static UserMapper userMapper = new UserMapper();
    private final static MemberMapper memberMapper = new MemberMapper();

	@Override
	public List<UserAuthoritiesEntity> loadByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<UserAuthoritiesEntity> loadByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		List<UserAuthoritiesEntity> userAuthorities = null;
		if(userEntity != null) {
			userAuthorities = roleRepository.findByUserId(userEntity.getId());
		}
		return userAuthorities;
	}
	@Override
	public void save(User user) {
		MemberEntity memberEntity = memberRepository.save(memberMapper.convertToEntity(user));
		UserEntity userEntity = (UserEntity)userMapper.convertToEntity(user);
		userEntity.setMember(memberEntity);
		userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
		userRepository.save(userEntity);
		
	}

}
