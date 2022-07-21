CREATE TABLE company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company(id, name) 
VALUES (1, 'company1'), (2, 'company2'), (3, 'company3'), (4, 'company4'), 
       (5, 'company5'), (6, 'company6');


INSERT INTO person(id, name, company_id) 
VALUES (1, 'Ivan', 1), (2, 'Sergey', 3), (3, 'Alexander', 3), (4, 'Maxim', 5), 
       (5, 'Oleg', 6), (6, 'Dmitriy', 1), (7, 'Lena', 3), (8, 'Olga', 4), 
       (9, 'Vera', 1), (10, 'Anastasiya', 1), (11, 'Petr', 2), (12, 'Nataliya', 3);

/*1*/
SELECT person.name "Имя", company.name "Название компании"
FROM person JOIN company ON person.company_id = company.id
WHERE company.id != 5
ORDER BY person.name;

/*2*/
SELECT company.name "Название компании", COUNT(person.name) "Количество персонала"
FROM person JOIN company ON person.company_id = company.id
GROUP BY company.name
HAVING COUNT(person.name) = (SELECT COUNT(person.name)
                            FROM person
                            GROUP BY person.company_id
                            ORDER BY COUNT(person.name) DESC
                            LIMIT 1);