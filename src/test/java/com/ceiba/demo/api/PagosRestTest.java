package com.ceiba.demo.api;


import com.ceiba.demo.DemoApplication;
import com.ceiba.demo.model.Pagos;
import com.ceiba.demo.repository.IPagosRepository;
import com.ceiba.demo.service.PagosService;
import com.ceiba.demo.service.dto.PagoDto;
import com.ceiba.demo.service.mapper.PagoMapper;
import com.ceiba.demo.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class PagosRestTest {

    private static final Integer DOC_IDENT_ARREN = 1;
    private static final Integer UPDATE_DOC_IDENT_ARREN = 2;
    private static final String COD_INMUEBLE = "AAAA";
    private static final String UPDATE_COD_INMUEBLE = "BBBB";

    private static final Integer VALOR_PAGADO = 1;
    private static final Integer UPDATE_VALOR_PAGADO = 2;

    private static final LocalDate FECHA_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATE_FECHA_PAGO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_PAGO = LocalDate.ofEpochDay(-1L);


    private static final String TEST_API_URL = "/api/pagos";

    private Pagos pagos;

    @Autowired
    private PagosService service;

    @Autowired
    private IPagosRepository repository;

    @Autowired
    private PagoMapper mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    public static Pagos createEntity() {
        return Pagos.builder()
                .documentoIdentificacionArrendatario(DOC_IDENT_ARREN)
                .codigoInmueble(COD_INMUEBLE)
                .valorPagado(VALOR_PAGADO)
                .fechaPago(FECHA_PAGO)
                .build();
    }

    public static Pagos createUpdateEntity() {
        return Pagos.builder()
                .documentoIdentificacionArrendatario(UPDATE_DOC_IDENT_ARREN)
                .codigoInmueble(UPDATE_COD_INMUEBLE)
                .valorPagado(UPDATE_VALOR_PAGADO)
                .fechaPago(UPDATE_FECHA_PAGO)
                .build();
    }

    @BeforeEach
    public void init() {
        pagos = createEntity();
    }

    @Test
    @Transactional
    void createPago() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(pagos)))
                .andExpect(status().isOk());

        List<Pagos> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);

        Pagos pagos = list.get(list.size() - 1);

        assertThat(pagos.getFechaPago()).isEqualTo(FECHA_PAGO);
        assertThat(pagos.getCodigoInmueble()).isEqualTo(COD_INMUEBLE);
        assertThat(pagos.getDocumentoIdentificacionArrendatario()).isEqualTo(DOC_IDENT_ARREN);
        assertThat(pagos.getValorPagado()).isEqualTo(VALOR_PAGADO);
    }



    @Test
    @Transactional
    void createPagoShouldFail() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(null)))
                .andExpect(status().is4xxClientError());

        List<Pagos> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRecords() throws Exception {
        repository.saveAndFlush(pagos);

        mockMvc.perform(get(TEST_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pagos.getId())))
                .andExpect(jsonPath("$.[*].documentoIdentificacionArrendatario").value(hasItem(DOC_IDENT_ARREN)))
                .andExpect(jsonPath("$.[*].codigoInmueble").value(hasItem(COD_INMUEBLE)))
                .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(FECHA_PAGO.toString())))
                .andExpect(jsonPath("$.[*].valorPagado").value(hasItem(VALOR_PAGADO)));

    }

    @Test
    @Transactional
    void checkCodigoInmuebleIsRequired() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        PagoDto pagoTest = mapper.toDto(this.pagos);
        pagoTest.setCodigoInmueble(null);

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(pagoTest)))
                .andExpect(status().is4xxClientError());

        List<Pagos> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentoIdentificacionArrendatarioIsRequired() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        PagoDto pagoTest = mapper.toDto(this.pagos);
        pagoTest.setDocumentoIdentificacionArrendatario(null);

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(pagoTest)))
                .andExpect(status().is4xxClientError());

        List<Pagos> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorPagadoIsRequired() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        PagoDto pagoTest = mapper.toDto(this.pagos);
        pagoTest.setValorPagado(null);

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(pagoTest)))
                .andExpect(status().is4xxClientError());

        List<Pagos> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);

        pagoTest.setValorPagado(2000000);

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(pagoTest)))
                .andExpect(status().is4xxClientError());

        List<Pagos> list2 = repository.findAll();
        assertThat(list2).hasSize(databaseSizeBeforeCreate);
    }
}
