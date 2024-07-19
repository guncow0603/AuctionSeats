insert into users
(id, created_at, modified_at, birth, email, is_deleted, name, nickname, password, phone_number, role)
select * from(
                 select 0 as id,
                        now() as created_at,
                        now() as modified_at,
                        "1999-01-01" as birth,
                        "admin@gmail.com" as email,
                        false as is_deleted,
                        "관리자" as name,
                        "일반관리자" as nickname,
                        "$2a$10$lNdW7CaSUD4N46XoAwXbX.rB3EmWyobNbkyccKHmt/o2zn.NgOVKW" as password,
                        "01012345678" as phone_number,
                        "ADMIN" as role
             ) as init
where not exists (
    select 1 from users
    where users.email = init.email
);