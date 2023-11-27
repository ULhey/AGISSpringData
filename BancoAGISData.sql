CREATE DATABASE BancoAGISData
GO
USE BancoAGISData

--PROCEDURE
CREATE PROCEDURE criarRA(@ra AS VARCHAR(9) OUTPUT) AS
	DECLARE @cont INT =  0
	SET @ra = '' + YEAR(GETDATE())
	IF (MONTH(GETDATE()) >= 6) BEGIN
		SET @ra = @ra + '2'
	 END
	 ELSE  BEGIN
		SET @ra = @ra + '1'
	 END
	WHILE(@cont < 4) BEGIN
		SET @ra = @ra +  CAST(CAST(RAND() * 10 AS INT) AS CHAR(1))
		SET @cont = @cont + 1
	END

CREATE PROCEDURE validarCPF(@cpf VARCHAR(11), @valido BIT OUTPUT) AS BEGIN
    DECLARE @soma1 INT,
					@soma2 INT,
					@i INT,
					@cpfValido BIT
    SET @soma1 = 0
    SET @soma2 = 0
    SET @cpfValido = 1 
	IF (LEN(@cpf) = 11) BEGIN
        SET @i = 1
       WHILE (@i <= 9) BEGIN
            SET @soma1 = @soma1 + CAST(SUBSTRING(@cpf, @i, 1) AS INT) * (11 - @i)
            SET @soma2 = @soma2 + CAST(SUBSTRING(@cpf, @i, 1) AS INT) * (12 - @i)
            SET @i = @i + 1
        END
			SET @soma1 = 11 - (@soma1 % 11)
        IF (@soma1 >= 10)
		SET @soma1 = 0
        SET @soma2 = @soma2 + @soma1 * 2
        SET @soma2 = 11 - (@soma2 % 11)
        IF (@soma2 >= 10)
		SET @soma2 = 0
        IF (@soma1 <> CAST(SUBSTRING(@cpf, 10, 1) AS INT) OR @soma2 <> CAST(SUBSTRING(@cpf, 11, 1) AS INT))
            SET @cpfValido = 0
		END
     ELSE BEGIN
        SET @cpfValido = 0
    END
    SET @valido = @cpfValido
END

CREATE PROCEDURE criarEMAILCORP(@nome VARCHAR(100), @email VARCHAR(100) OUTPUT) AS
BEGIN
    DECLARE @pnome VARCHAR(50),
					 @unome VARCHAR(50),
					 @espaco INT,
					 @uespaco INT

    SET @uespaco = CHARINDEX(' ', REVERSE(@nome))

    IF (@uespaco > 0) BEGIN
        SET @unome = RIGHT(@nome, @uespaco - 1)
		END
    ELSE BEGIN
        SET @unome = @nome
    END

    SET @espaco = CHARINDEX(' ', @nome)

    IF (@espaco > 0) BEGIN
        SET @pnome = LEFT(@nome, @espaco - 1)
		END
    ELSE BEGIN
        SET @pnome = @nome
    END

    SET @email = @pnome + '.' + @unome + '@agis.com'
END

CREATE PROCEDURE validaIDADE(@DataNasc DATE, @valido BIT OUTPUT) AS
	DECLARE @i DATE,
					@idade	 INT
	SET @i = (SELECT GETDATE())
	SET @idade = DATEDIFF(DAY, @DataNasc, @i) / 365
	IF (@idade >= 16)  BEGIN
		SET @valido = 1
	END
	ELSE BEGIN
		SET @valido = 0
	END

CREATE PROCEDURE gerarSEMESTRE(@semesInicio AS INT OUTPUT) AS
		IF (MONTH(GETDATE()) > 6) BEGIN
			SET @semesInicio =  2
			END
			ELSE BEGIN
			SET @semesInicio =  1
			END

CREATE PROCEDURE gerarANO(@anoInicio AS INT OUTPUT) AS
	SET @anoInicio = YEAR(GETDATE())

--TRIGGERs
CREATE TRIGGER MatriculaAuto
ON Aluno AFTER INSERT
AS BEGIN
    INSERT INTO Matricula (ano, semestreMatricula,  situacao,  raAluno, idTurma)
    SELECT
		CAST(YEAR(GETDATE()) AS INT), 
		CASE WHEN MONTH(GETDATE()) > 6 THEN '2' ELSE '1' END AS semestreMatricula,
		'Cursando' AS situacao,
        i.RA AS RA,
        t.idTurma AS Turma
    FROM inserted i
			inner join curso c ON c.idCurso = i.idCurso
			inner join disciplina d ON  d.idCurso = c.idCurso
			inner join turma t ON t.idDisciplina = d.idDisciplina
	WHERE semestreDisciplina = 1
END

ENABLE TRIGGER MatriculaAuto ON Aluno

CREATE TRIGGER ProibirExclusaoMatricula
ON Matricula INSTEAD OF DELETE
AS BEGIN
	ROLLBACK TRANSACTION
    RAISERROR('Não é permitido excluir a matrícula de um aluno em uma disciplina.', 16, 1);
END

ENABLE TRIGGER ProibirExclusaoMatricula ON MatriculaAlunoDisciplina

CREATE TRIGGER ProibirExclusaoChamada
ON ChamadaLista INSTEAD OF DELETE
AS BEGIN
	ROLLBACK TRANSACTION
    RAISERROR('Não é permitido excluir uma chamada já realizada.', 16, 1);
END

ENABLE TRIGGER ProibirExclusaoChamada ON ChamadaLista


--FUNCTION
CREATE FUNCTION GerarListaChamada(@idTurma INT)
RETURNS @table TABLE (
		RAAluno VARCHAR(9),
		NomeAluno VARCHAR(100),
		NomeDisciplina VARCHAR(100),
		idTurma BIGINT
) AS BEGIN
    INSERT INTO @table (RAAluno, NomeAluno, NomeDisciplina, idTurma)
    SELECT 
		m.raAluno,
		a.nome,
		d.nomeDisciplina,
		t.idTurma
	FROM Matricula m
		INNER JOIN Aluno a ON m.raAluno = a.RA
		INNER JOIN Turma t ON m.idTurma = t.idTurma
		INNER JOIN Disciplina d ON d.idDisciplina = t.idDisciplina
	WHERE t.idTurma = @idTurma
    RETURN
END


CREATE alter FUNCTION historicoAluno(@ra AS VARCHAR(9))
RETURNS @table TABLE(
	idDisciplina INT,
	nomeDisciplina VARCHAR(100),
	professor VARCHAR (100),
	metodoAvaliativo VARCHAR(30),
	notaFinal FLOAT,
	totalFaltas INT
)
AS BEGIN
INSERT INTO @table(idDisciplina, nomeDisciplina, professor, metodoAvaliativo, notafinal, totalFaltas)
SELECT 
	d.idDisciplina,
	d.nomeDisciplina,
	tu.professor,	
	tu.metodoAvaliativo,
	CASE
    WHEN tu.metodoAvaliativo = 'nota1' THEN (a.p1 * 0.3) + (a.p2 * 0.5) + (a.t * 0.2)
    WHEN tu.metodoAvaliativo = 'nota2' THEN (a.p1 * 0.35) + (a.p2 * 0.35) + (a.t * 0.3)
    WHEN tu.metodoAvaliativo = 'nota3' THEN (a.p1 * 0.33) + (a.p2 * 0.33) + (a.t * 0.3)
    ELSE (a.p1* 0.8) + (a.p2 * 0.2)
    END AS mediaFinal,
	SUM	(c.faltas) AS totalFaltas
FROM Disciplina d
	INNER JOIN turma tu ON d.idDisciplina = tu.idDisciplina
	INNER JOIN matricula m ON m.idTurma = tu.idTurma
	INNER JOIN avalicao a ON  a.idMatricula = m.idMatricula
	INNER JOIN chamada c ON m.raAluno = c.raAluno
WHERE c.raAluno = @ra
GROUP BY d.idDisciplina, d.nomeDisciplina, tu.professor, tu.metodoAvaliativo, a.p1, a.p2, a.t
RETURN
END

CREATE FUNCTION historicoNotas(@turma INT)
RETURNS @table TABLE(
	idTurma INT,
	periodoAula VARCHAR(100),
	nomeDisciplina VARCHAR (100),
	metodoAvaliativo VARCHAR(30),
	p1 FLOAT,
	p2 FLOAT,
	t FLOAT,
	mediaFinal FLOAT
)
AS BEGIN
INSERT INTO @table(idTurma, periodoAula, nomeDisciplina, metodoAvaliativo, p1, p2, t, mediaFinal)
SELECT 
	tu.idTurma,
	tu.periodoAula,
	d.nomeDisciplina,
	tu.metodoAvaliativo,
	a.p1,
	a.p2,
	a.t,
	CASE
    WHEN tu.metodoAvaliativo = 'nota1' THEN (a.p1 * 0.3) + (a.p2 * 0.5) + (a.t * 0.2)
    WHEN tu.metodoAvaliativo = 'nota2' THEN (a.p1 * 0.35) + (a.p2 * 0.35) + (a.t * 0.3)
    WHEN tu.metodoAvaliativo = 'nota3' THEN (a.p1 * 0.33) + (a.p2 * 0.33) + (a.t * 0.3)
    ELSE (a.p1* 0.8) + (a.p2 * 0.2)
    END AS mediaFinal
FROM turma tu
	INNER JOIN disciplina d ON d.idDisciplina = tu.idDisciplina
	INNER JOIN avalicao a ON a.idTurma = tu.idTurma
WHERE tu.idTurma = @turma
RETURN
END


CREATE FUNCTION situacaoNotaFaltas(@turma INT)
RETURNS @table TABLE (
    idDisciplina INT,
    nomeDisciplina VARCHAR(100),
    professor VARCHAR(100),
    metodoAvaliativo VARCHAR(30),
    mediaFinal FLOAT,
    totalFaltas INT,
    statusAluno VARCHAR(15)
)
AS 
BEGIN
    DECLARE @idDisciplina INT,
            @nomeDisciplina VARCHAR(100),
            @professor VARCHAR(100),
            @metodoAvaliativo VARCHAR(30),
            @mediaFinal FLOAT,
            @totalFaltas INT,
            @TotalAulasSemestre INT,
            @quantidadeAula INT,
            @statusAluno VARCHAR(15)

    DECLARE cursor_situacao CURSOR FOR
        SELECT 
            tu.idTurma,
            d.nomeDisciplina,
            tu.professor,
            tu.metodoAvaliativo,
            CASE
                WHEN tu.metodoAvaliativo = 'nota1' THEN (a.p1 * 0.3) + (a.p2 * 0.5) + (a.t * 0.2)
                WHEN tu.metodoAvaliativo = 'nota2' THEN (a.p1 * 0.35) + (a.p2 * 0.35) + (a.t * 0.3)
                WHEN tu.metodoAvaliativo = 'nota3' THEN (a.p1 * 0.33) + (a.p2 * 0.33) + (a.t * 0.3)
                ELSE (a.p1 * 0.8) + (a.p2 * 0.2)
            END AS mediaFinal,
            SUM(c.faltas) AS totalFaltas,
            d.quantidadeAula
        FROM Disciplina d
            INNER JOIN turma tu ON d.idDisciplina = tu.idDisciplina
            INNER JOIN avalicao a ON a.idTurma = tu.idTurma
            INNER JOIN chamada c ON c.idTurma = tu.idTurma
        WHERE tu.idTurma = @turma
        GROUP BY tu.idTurma, d.nomeDisciplina, tu.professor, tu.metodoAvaliativo, a.p1, a.p2, a.t, d.quantidadeAula;

    OPEN cursor_situacao

    FETCH NEXT FROM cursor_situacao INTO @idDisciplina, @nomeDisciplina, @professor, @metodoAvaliativo, @mediaFinal, @totalFaltas, @quantidadeAula;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @TotalAulasSemestre = @quantidadeAula

        SET @statusAluno = CASE
            WHEN ((@TotalAulasSemestre * 0.75) + @totalFaltas) > @TotalAulasSemestre THEN 'Reprovado'
            ELSE 'Aprovado'
        END

        INSERT INTO @table (idDisciplina, nomeDisciplina, professor, metodoAvaliativo, mediaFinal, totalFaltas, statusAluno)
        VALUES (@idDisciplina, @nomeDisciplina, @professor, @metodoAvaliativo, @mediaFinal, @totalFaltas, @statusAluno);

        FETCH NEXT FROM cursor_situacao INTO @idDisciplina, @nomeDisciplina, @professor, @metodoAvaliativo, @mediaFinal, @totalFaltas, @quantidadeAula;
    END;

    CLOSE cursor_situacao
    DEALLOCATE cursor_situacao

    RETURN
END

--SELECT FUNCTIONs
SELECT * FROM situacaoNotaFaltas(503)
SELECT * FROM historicoNotas(502)
SELECT * FROM historicoAluno('202321126')


--SELECTs
SELECT * FROM matricula 
SELECT * FROM disciplina
SELECT * FROM turma 
SELECT * FROM aluno
SELECT * FROM curso
SELECT * FROM avalicao
SELECT * FROM chamada 