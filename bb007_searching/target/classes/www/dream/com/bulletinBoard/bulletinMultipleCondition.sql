create table s_post{
	id			varchar2(4000)		primary key,
	board_id	number(9)			REFERENCE s_board(id),
	writer_id	varchar2(10)		REFERENCE s_party(user_id),
	title		varchar2(100),
	content		varchar2(4000),
}

select p.*
  from s_post p
where board_id = 3
  and (
  	  title like '%Monody%'
  	  or
  	  content like '%TheFatRat%'
  	)
  order by id desc
	offset 10 rows fetch first 10 rows only
	

SELECT * FROM BLOG --안에 아무 내용이 없다면 where 절 안에 아무것도 안들어감, 결과는 SELECT * FROM BLOG
  <where>
  </where>
  

  SELECT * FROM BLOG
  where
         state = #{state} -- <if test="state != null"> 이 조건만 살아 있음 
  
  SELECT * FROM BLOG
  <where>
         state = #{state} -- <if test="state != null"> 이 조건이 있으니 출력
        AND title like #{title} -- <if test="title != null"> 이 조건이 있으니 출력
  </where>
  
  
  SELECT * FROM BLOG
  <where>
--state = #{state} -- <if test="state != null"> 이 조건은 없음
  		AND title like #{title} -- <if test="title != null"> 이 조건은 있음
        AND author_name like #{author.name} -- <if test="author != null and author.name != null"> 이 조건은 있음
        --이렇게 되면 아래와 같이 바뀐다.
        
        where					-- where Tag가 되고
        title like #{title}  -- 앞의 AND가 떨어진다.
        AND author_name like #{author.name} -- 이렇게되어야 구문상에서 오류가 안난다.
  </where>
  
  --여기 부분, 생각보다 많이 쓰인다. 동적 Query.
  
  
	--forEach Tag도 많이 사용한다.  
  list={a b c}
  <select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P 
  WHERE ID in (a,b,c) -- ID에 들어있는 column의 값이 a,b,c 일때 조회, in은 or와 같다고 봐도 됨
  <foreach item="item" collection="list" -- collection에 있는 list들의 요소들을 바탕으로 item이라고 하자.
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>


Map<String, String> myMap = new HashMap<>();
myMap.put("T", "사랑");
myMap.put("C", "행복");

select *
  from s_post
<trim prefix = "where ("suffix=")" prefixOverrides="OR  ">
	<forEach collection="myMap" item="value" index="key">
		<trim prefix="or">
			<if test="key == 'C'.toString()">
				content = #{value}
			</if>
			<if test="key == 'T'.toString()">
				title = #{value}
			</if>
		</trim>
	</forEach>
</trim>

select *
  from s_post
 where(	content = '행복'
 		or title = '사랑'
 		)		