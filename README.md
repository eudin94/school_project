<h1>Bem-vindo a API de sistema de escola</h1>

Pelo terminal, utilizar o comando 'docker-compose up' para iniciar o banco de dados através do docker.<br>

Executar a API pela classe 'Application', localizada em 'src/main/java/com/comerlato/school_project'
<h2>Portas utilizadas pela aplicação:</h2>

- *5445* - Postgres
- *8099* - Swagger

<h2>Link para acessar o swagger:</h2>
http://localhost:8099/api/school-project/swagger-ui/

<h2>O sistema de migrations executará um dump para o banco não ficar totalmente limpo</h2>
```
Departments

Name                        Location
'Human Sciences'            'Building A, Room 101'
'Languages and Codes'       'Building A, Room 301'
'Natural Sciences'          'Building B, Room 101'
'Math'                      'Building B, Room 301'
```

```
Instructors

Department Name         Headed By     First Name    Last Name     Phone
'Human Sciences'        ''            'John'        'Carim'       '51999999991'
'Languages and Codes'   ''            'James'       'Morne'       '51999999992'
'Natural Sciences'      ''            'Laura'       'Oolacile'    '51999999993'
'Math'                  ''            'Anna'        'Lordran'     '51999999994'
```
<h6>Segundo o roteiro do exercício, o campo 'Headed By' faz parte dos instrutores, então o fiz dessa forma.
Ao meu ver, parece fazer mais sentido como parte do departamento.</h6>

```
Courses

Department Name         Instructor Id   Duration  Name
'Human Sciences'        '1'             '8'       'Social Sciences'
'Human Sciences'        '1'             '8'       'Philosophy'
'Languages and Codes'   '2'             '6'       'Sign Language'
'Languages and Codes'   '2'             '8'       'Portuguese'
'Natural Sciences'      '3'             '10'      'Astrophysics'
'Natural Sciences'      '3'             '10'      'Biology'
'Math'                  '4'             '8'       'Accounting Sciences'
'Math'                  '4'             '8'       'Statistics'
```

```
Students

First Name    Last Name     Phone
'Anderson'    'Balder'      '51999999995'
'Mark'        'Astora'      '51999999996'
'Giullia'     'Berenike'    '51999999997'
'Jennifer'    'Catarina'    '51999999998'
```