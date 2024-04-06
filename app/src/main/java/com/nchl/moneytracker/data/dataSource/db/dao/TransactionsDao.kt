/*
package global.citytech.finpos.merchant.data.datasource.app.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import global.citytech.finpos.merchant.domain.model.app.TransactionLog

*/
/**
 * Created by Rishav Chudal on 9/22/20.
 *//*

@Dao
interface TransactionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactionLogs: List<TransactionLog>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(transactionLog: TransactionLog): Long

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, receipt_log, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE invoice_num = :invoiceNumber AND transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER)")
    fun getTransactionLogBy(invoiceNumber: String, batchNumber: String): TransactionLog?

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, receipt_log, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE invoice_num = :invoiceNumber AND transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType")
    fun getTransactionLogByInvoiceNumberAndTransactionType(
        invoiceNumber: String,
        batchNumber: String,
        transactionType: String
    ): TransactionLog?

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, receipt_log, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE invoice_num = :invoiceNumber AND transaction_status = 'APPROVED'")
    fun getTransactionLogByInvoiceNumberWithoutBatchNumber(invoiceNumber: String): TransactionLog?

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE auth_code = :authCode AND transaction_status == 'APPROVED'")
    fun getTransactionLogByAuthCode(authCode: String): TransactionLog?

    @Query(
        "SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id, invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE transaction_status = 'APPROVED' ORDER BY invoice_num DESC LIMIT 1 "
    )
    fun getRecentApprovedTransaction(): TransactionLog?

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions ORDER BY invoice_num DESC LIMIT 1 ")
    fun getRecentTransaction(): TransactionLog?

    @Query("SELECT COUNT(stan) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType")
    fun getCountByTransactionType(batchNumber: String, transactionType: String): Long

    @Query("SELECT SUM(transaction_amount) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType")
    fun getSumByTransactionType(batchNumber: String, transactionType: String): Long

    @Query("SELECT COUNT(stan) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND read_card_response LIKE :cardScheme")
    fun getCountByTransactionTypeWithCardScheme(
        cardScheme: String,
        batchNumber: String,
        transactionType: String
    ): Long

    @Query("SELECT SUM(transaction_amount) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND read_card_response LIKE :cardScheme")
    fun getSumByTransactionTypeWithCardScheme(
        cardScheme: String,
        batchNumber: String,
        transactionType: String
    ): Long

    @Query("SELECT COUNT(stan) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND original_transaction_type = :originalTransactionType")
    fun getCountByOriginalTransactionType(
        batchNumber: String,
        transactionType: String,
        originalTransactionType: String
    ): Long

    @Query("SELECT SUM(original_transaction_amount) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND original_transaction_type = :originalTransactionType")
    fun getSumByOriginalTransactionType(
        batchNumber: String,
        transactionType: String,
        originalTransactionType: String
    ): Long

    @Query("SELECT SUM(original_transaction_amount) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND original_transaction_type = :originalTransactionType AND read_card_response LIKE :cardScheme")
    fun getSumByOriginalTransactionTypeWithCardScheme(
        cardScheme: String,
        batchNumber: String,
        transactionType: String,
        originalTransactionType: String
    ): Long

    @Query("SELECT COUNT(original_transaction_amount) FROM transactions WHERE transaction_status = 'APPROVED' AND CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_type = :transactionType AND original_transaction_type = :originalTransactionType AND read_card_response LIKE :cardScheme")
    fun getCountByOriginalTransactionTypeWithCardScheme(
        cardScheme: String,
        batchNumber: String,
        transactionType: String,
        originalTransactionType: String
    ): Long

    @Query("UPDATE transactions SET reconcile_status = 'RECONCILED', reconcile_time = :reconciledTime, reconcile_date = :reconciledDate  WHERE CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER)")
    fun updateReconcileStatus(
        batchNumber: String,
        reconciledDate: String,
        reconciledTime: String
    ): Int

    @Query("SELECT stan, receipt_log, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id,invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response, transaction_voided, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_status = 'APPROVED'")
    fun getTransactionLogsByBatchNumber(batchNumber: String): List<TransactionLog>

    @Query("SELECT COUNT(stan) FROM transactions WHERE CAST(reconcile_batch_no AS INTEGER) = CAST(:batchNumber as INTEGER) AND transaction_status = 'APPROVED'")
    fun getTransactionCountByBatchNumber(batchNumber: String): Long

    @Query("DELETE FROM transactions WHERE transaction_type != 'PRE_AUTH' AND authorization_completed != 0")
    fun deleteAll()

    @Query("UPDATE transactions SET authorization_completed = :authorizationCompleted WHERE transaction_type = :transactionType AND auth_code = :authCode")
    fun updatePreAuthCompletionStatus(
        authorizationCompleted: Boolean,
        transactionType: String, authCode: String
    ): Int

    @Query("UPDATE transactions SET tip_adjusted = :tipAdjusted WHERE transaction_type = :transactionType AND invoice_num = :invoiceNumber")
    fun updateTipAdjustedStatus(
        tipAdjusted: Boolean,
        transactionType: String,
        invoiceNumber: String
    ): Int

    @Query("SELECT stan, response_code, authorization_completed, original_txn_ref_number,terminal_id,merchant_id, invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response,receipt_log, tip_adjusted,transaction_voided, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE stan = :stan")
    fun getTransactionLogsByStan(stan: String): TransactionLog

    @Query("SELECT stan,original_txn_ref_number,terminal_id,merchant_id, invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response,receipt_log, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE transaction_status = 'APPROVED'")
    fun getApprovedTransactions(): List<TransactionLog>

    @Query("SELECT stan,original_txn_ref_number,terminal_id,merchant_id, invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response,receipt_log, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE transaction_status = 'APPROVED' ORDER BY STAN DESC LIMIT :limit OFFSET :offset")
    fun getApprovedTransactions(limit: Int, offset: Int): List<TransactionLog>

    @Query("SELECT stan,original_txn_ref_number,terminal_id,merchant_id, invoice_num, retrieval_reference_number, auth_code, transaction_type, original_transaction_type, transaction_amount, original_transaction_amount, transaction_date, transaction_time, transaction_status, CASE WHEN pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE pos_entry_mode END pos_entry_mode, CASE WHEN original_pos_entry_mode = '\"CONTACT_LESS\"' THEN '\"PICC\"' ELSE original_pos_entry_mode END original_pos_entry_mode, pos_condition_code, original_pos_condition_code, reconcile_status, reconcile_time, reconcile_date, reconcile_batch_no, read_card_response,receipt_log, tip_adjusted, is_emi_trnasaction, emi_info,currency_code,vat_info FROM transactions WHERE transaction_status = 'APPROVED' AND invoice_num LIKE '%'||:searchParam ||'%' ORDER BY STAN DESC LIMIT :limit OFFSET :offset")
    fun searchApprovedTransactions(
        searchParam: String,
        limit: Int,
        offset: Int
    ): List<TransactionLog>

    @Query("SELECT COUNT(*) FROM transactions WHERE transaction_status = 'APPROVED' AND invoice_num LIKE '%'||:searchParam ||'%' ORDER BY STAN DESC")
    fun countSearchApprovedTransactions(searchParam: String): Int

    @Query("SELECT COUNT(*) FROM transactions WHERE transaction_status = 'APPROVED'")
    fun countApprovedTransactions(): Int

    @Query("DELETE FROM transactions WHERE stan = :stan")
    fun deleteByStan(stan: String)

    @Query("UPDATE transactions SET transaction_voided = :transactionVoided WHERE transaction_type = :transactionType AND invoice_num = :invoiceNumber")
    fun updateOnVoidStatusForGivenTransactionDetails(
        transactionVoided: Boolean,
        transactionType: String,
        invoiceNumber: String
    ): Int

    @Query("SELECT invoice_num FROM transactions WHERE retrieval_reference_number = :rrn")
    fun getInvoiceNumberByRrn(rrn: String): String
}*/
