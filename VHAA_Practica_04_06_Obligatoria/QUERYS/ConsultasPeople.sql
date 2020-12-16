--Consultar más cabellos repetidos
select hair_color, count(hair_color) as cantidad from people p2
where hair_color not like '%none%' and hair_color not like '%n/a%'
group by hair_color
order by cantidad desc
limit 1

--Consultar people mas films 
select p.name,  COUNT(codigo_film) as veces 
from films_people fp inner join people p 
on( fp.codigo_people  = p.codigo)
group by p.name  
order by veces desc

