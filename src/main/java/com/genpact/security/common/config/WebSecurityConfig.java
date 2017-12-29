package com.genpact.security.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()/*.antMatchers("/" ,"/demo/**" ).permitAll()*/.anyRequest().authenticated().and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/index").permitAll()
			.failureUrl("/login?error").permitAll().and()
		.logout().permitAll();

	}

	/**
	 * 忽略需要认证的路径
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/bootstrap/**", "/druid/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, @Value("${spring.profiles.active}") String env) throws Exception {
		if ("dev".equals(env)) {
			auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "ACTUATOR");
		} else {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	}

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

}