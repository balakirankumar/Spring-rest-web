SET search_path TO rest;


insert into rest.user_profile(id, email, first_name, last_name,birth_date)
    values('4bd8f699-b078-4573-8b9e-d4a5313d4e4d','bala@kiran.com','Bala','Kiran','2000-10-29');

insert into rest.user_profile(id, birth_date,email, first_name, last_name)
    values('89b82e35-f6c3-48ff-a905-0c2a3ca48deb','2001-10-29','bala@kumar.com','Bala','Kumar');

insert into rest.user_profile(id, birth_date, email, first_name, last_name)
    values('5627cb5d-57b3-4cf2-9744-b3bcbda4a8b1','2002-10-29','bala@bala.com','Bala','Bala');


insert into rest.post(id, title, description, user_id, created_by)
    values('b5cb5136-3a99-47cd-9429-df744189f02b','First Post title','First Post Description',
            '4bd8f699-b078-4573-8b9e-d4a5313d4e4d','4bd8f699-b078-4573-8b9e-d4a5313d4e4d');

insert into rest.post(id, title, description, user_id, created_by)
    values('b4373ef7-3445-4b61-8458-4dc81c61ac74','Second Post Description','Second Post title',
            '5627cb5d-57b3-4cf2-9744-b3bcbda4a8b1','5627cb5d-57b3-4cf2-9744-b3bcbda4a8b1');
