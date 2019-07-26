package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.FilePrice;

import java.util.Date;
import java.util.List;

public interface IFilePriceDAO extends CRUD<FilePrice> {

	Date getLastDownloadedDate();

	List<FilePrice> getAll(boolean processed);

	void setProcessed(long id);

}