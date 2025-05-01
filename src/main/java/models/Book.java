package models;

import java.util.Date;
import java.util.List;

public class Book {
	private int id;
	private String title, writer, writerId;
	private Integer price, rate, sales;
	private List<Review> reviews;
	private Date publishDate;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	public Integer getSales() {
		return sales;
	}
	
	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Book(String title, String writer, String writerId, Integer price, Integer rate, Integer sales, List<Review> reviews,
			Date publishDate) {
		super();
		this.title = title;
		this.writer = writer;
		this.writerId = writerId;
		this.price = price;
		this.rate = rate;
		this.sales = sales;
		this.reviews = reviews;
		this.publishDate = publishDate;
	}
	
	public Book(int id, String title, String writer, String writerId, Integer price, Integer rate, Integer sales, List<Review> reviews,
			Date publishDate) {
		super();
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.writerId = writerId;
		this.price = price;
		this.rate = rate;
		this.sales = sales;
		this.reviews = reviews;
		this.publishDate = publishDate;
	}
	
	public Book(String title, String writer, String writerId, Integer price, Date publishDate) {
		super();
		this.title = title;
		this.writer = writer;
		this.writerId = writerId;
		this.price = price;
		this.publishDate = publishDate;
	}

}
