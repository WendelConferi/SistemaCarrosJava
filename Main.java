package com.wendel.automotiva.vo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	protected static String url = "jdbc:postgresql://localhost:5432/wendel";
	protected static final String user = "postgres";
	protected static final String password = "postgres";

	static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		int opcaoMenuSelecionada = 0;

		imprimirMenu();
		opcaoMenuSelecionada = teclado.nextInt();

		while (opcaoMenuSelecionada != 1) {

			if (opcaoMenuSelecionada == 2) {
				inserir();

				imprimirMenu();
				opcaoMenuSelecionada = teclado.nextInt();
			}

			if (opcaoMenuSelecionada == 3) {
				listar();

				imprimirMenu();
				opcaoMenuSelecionada = teclado.nextInt();
			}
			if (opcaoMenuSelecionada == 4) {
				remover();

				imprimirMenu();
				opcaoMenuSelecionada = teclado.nextInt();
			}
			if (opcaoMenuSelecionada == 5) {
				editar();

				imprimirMenu();
				opcaoMenuSelecionada = teclado.nextInt();
			}
		}

	}

	private static void imprimirMenu() {
		System.out.println("--MENU--");
		System.out.println("Digite 1 para sair");
		System.out.println("Digite 2 para inserir");
		System.out.println("Digite 3 para listar");
		System.out.println("Digite 4 para remover");
		System.out.println("Digite 5 para editar");
	}

	private static void inserir() throws Exception {

		System.out.println("\n\ninserindo carro...");

		CarrosVO c = new CarrosVO();

		System.out.println("Digite a marca do carro");
		String desMarca = teclado.next();

		System.out.println("Digite o modelo do carro");
		String desModelo = teclado.next();

		System.out.println("Digite o ano do carro");
		int numAno = teclado.nextInt();
		c.setNumAno(numAno);

		System.out.println("Digite  valor do carro");
		int vlrCarro = teclado.nextInt();

		System.out.println("Digite 1 para inserir o proprietario ou 2 para deixar vazio");
		int v = teclado.nextInt();

		String desNomeProprietario = null;

		if (v == 1) {
			System.out.println("Digite o nome do proprietário");
			desNomeProprietario = teclado.next();
		} else if (v == 2) {
			desNomeProprietario = null;
		} else {
			System.out.println("Opção inválida, será colocado vazio");
		}

		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement smt = conn.prepareStatement(
				"INSERT INTO carros(des_marca, des_modelo, vlr_carro, num_ano, des_nome_proprietario) VALUES (?,?,?,?,?)");
		smt.setString(1, desMarca);
		smt.setString(2, desModelo);
		smt.setInt(3, vlrCarro);
		smt.setInt(4, numAno);
		smt.setString(5, desNomeProprietario);

		smt.execute();

		smt.close();

		conn.close();

		System.out.println("Inserido!");
	}

	private static void listar() throws SQLException {

		System.out.println("\nLista de carros...");

		String sql = "SELECT * FROM carros";

		Connection conn = DriverManager.getConnection(url, user, password);

		Statement smt = conn.createStatement();
		ResultSet rs = smt.executeQuery(sql);

		int linha = 1;
		while (rs.next()) {

			System.out.println("registro numero " + linha);

			System.out.println(rs.getInt("cod_carro"));
			System.out.println(rs.getString("des_marca"));
			System.out.println(rs.getString("des_modelo"));
			System.out.println(rs.getString("vlr_carro"));
			System.out.println(rs.getString("num_ano"));
			System.out.println(rs.getString("des_nome_proprietario"));

			System.out.println("");

			linha++;

		}

		rs.close();

		smt.close();

		conn.close();

	}

	private static void remover() throws SQLException {
		System.out.println("Digite o codigo do qual quer remover");

		int codigoSelecionado = teclado.nextInt();
		Connection conn = DriverManager.getConnection(url, user, password);

		String sql = "DELETE FROM carros WHERE cod_carro = ?";
		PreparedStatement smt = conn.prepareStatement(sql);
		smt.setInt(1, codigoSelecionado);
		smt.execute();

		smt.close();

		conn.close();

		System.out.println("Removido");
	}

	private static void editar() throws SQLException {
		System.out.println("Digite o codigo do qual quer editar");

		int codigoSelecionado = teclado.nextInt();

		System.out.println("Digite a marca do carro");
		String desMarca = teclado.next();

		System.out.println("Digite o modelo do carro");
		String desModelo = teclado.next();

		System.out.println("Digite o ano do carro");
		int numAno = teclado.nextInt();

		System.out.println("Digite  valor do carro");
		int vlrCarro = teclado.nextInt();

		System.out.println("Digite 1 para inserir o proprietario ou 2 para deixar vazio");
		int v = teclado.nextInt();

		String desNomeProprietario = null;

		if (v == 1) {
			System.out.println("Digite o nome do proprietário");
			desNomeProprietario = teclado.next();
		} else if (v == 2) {
			desNomeProprietario = null;
		} else {
			System.out.println("Opção inválida, será atualizado para vazio");
		}

		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement smt = conn.prepareStatement(
				"UPDATE carros SET des_marca = ? ,des_modelo = ? , vlr_carro = ? , num_ano = ?, des_nome_proprietario = ? WHERE cod_carro = ?");
		smt.setString(1, desMarca);
		smt.setString(2, desModelo);
		smt.setInt(3, vlrCarro);
		smt.setInt(4, numAno);
		smt.setString(5, desNomeProprietario);
		smt.setInt(6, codigoSelecionado);
		smt.execute();

		smt.close();

		conn.close();

		System.out.println("Atualizado");
	}

}
