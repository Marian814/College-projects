library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (7 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end test_env;

architecture Behavioral of test_env is

component MPG is
    Port ( enable : out STD_LOGIC;
           btn : in STD_LOGIC;
           clk : in STD_LOGIC);
end component;

component SSD is
    Port ( clk : in STD_LOGIC;
           digits : in STD_LOGIC_VECTOR(31 downto 0);
           an : out STD_LOGIC_VECTOR(7 downto 0);
           cat : out STD_LOGIC_VECTOR(6 downto 0));
end component;

component IFetch
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           en : in STD_LOGIC;
           BranchAddress : in STD_LOGIC_VECTOR(31 downto 0);
           JumpAddress : in STD_LOGIC_VECTOR(31 downto 0);
           Jump : in STD_LOGIC;
           PCSrc : in STD_LOGIC;
           Instruction : out STD_LOGIC_VECTOR(31 downto 0);
           PCp4 : out STD_LOGIC_VECTOR(31 downto 0));
end component;

component ID
    Port ( clk : in STD_LOGIC;
           en : in STD_LOGIC;    
           Instr : in STD_LOGIC_VECTOR(25 downto 0);
           WD : in STD_LOGIC_VECTOR(31 downto 0);
           RegWrite : in STD_LOGIC;
           WriteAddress : in STD_LOGIC_VECTOR(4 downto 0);
           rt : out std_logic_vector(4 downto 0);
           rd : out std_logic_vector(4 downto 0);
           ExtOp : in STD_LOGIC;
           RD1 : out STD_LOGIC_VECTOR(31 downto 0);
           RD2 : out STD_LOGIC_VECTOR(31 downto 0);
           Ext_Imm : out STD_LOGIC_VECTOR(31 downto 0);
           func : out STD_LOGIC_VECTOR(5 downto 0);
           sa : out STD_LOGIC_VECTOR(4 downto 0));
end component;

component UC
    Port ( Instr : in STD_LOGIC_VECTOR(5 downto 0);
           RegDst : out STD_LOGIC;
           ExtOp : out STD_LOGIC;
           ALUSrc : out STD_LOGIC;
           Branch : out STD_LOGIC;
           Jump : out STD_LOGIC;
           ALUOp : out STD_LOGIC_VECTOR(2 downto 0);
           MemWrite : out STD_LOGIC;
           MemtoReg : out STD_LOGIC;
           RegWrite : out STD_LOGIC);
end component;

component EX is
    Port ( PCp4 : in STD_LOGIC_VECTOR(31 downto 0);
           RD1 : in STD_LOGIC_VECTOR(31 downto 0);
           RD2 : in STD_LOGIC_VECTOR(31 downto 0);
           Ext_Imm : in STD_LOGIC_VECTOR(31 downto 0);
           func : in STD_LOGIC_VECTOR(5 downto 0);
           sa : in STD_LOGIC_VECTOR(4 downto 0);
           ALUSrc : in STD_LOGIC;
           ALUOp : in STD_LOGIC_VECTOR(2 downto 0);
           BranchAddress : out STD_LOGIC_VECTOR(31 downto 0);
           ALURes : out STD_LOGIC_VECTOR(31 downto 0);
           Zero : out STD_LOGIC;
           rt : in std_logic_vector(4 downto 0);
           rd : in std_logic_vector(4 downto 0);
           RegDst : in std_logic;
           rWA : out std_logic_vector(4 downto 0));
end component;

component MEM
    port ( clk : in STD_LOGIC;
           en : in STD_LOGIC;
           ALUResIn : in STD_LOGIC_VECTOR(31 downto 0);
           RD2 : in STD_LOGIC_VECTOR(31 downto 0);
           MemWrite : in STD_LOGIC;			
           MemData : out STD_LOGIC_VECTOR(31 downto 0);
           ALUResOut : out STD_LOGIC_VECTOR(31 downto 0));
end component;

signal Instruction, PCp4, RD1, RD2, WD, Ext_imm : STD_LOGIC_VECTOR(31 downto 0); 
signal JumpAddress, BranchAddress, ALURes, ALURes1, MemData : STD_LOGIC_VECTOR(31 downto 0);
signal func : STD_LOGIC_VECTOR(5 downto 0);
signal sa : STD_LOGIC_VECTOR(4 downto 0);
signal zero : STD_LOGIC;
signal digits : STD_LOGIC_VECTOR(31 downto 0);
signal en, rst, PCSrc : STD_LOGIC;
-- main controls 
signal RegDst, ExtOp, ALUSrc, Branch, Jump, MemWrite, MemtoReg, RegWrite : STD_LOGIC;
signal ALUOp : STD_LOGIC_VECTOR(2 downto 0);

signal reg_if_id : std_logic_vector(63 downto 0);
signal reg_id_ex : std_logic_vector(157 downto 0);
signal reg_ex_mem : std_logic_vector(105 downto 0);
signal reg_mem_wb : std_logic_vector(70 downto 0);
signal rt, rd : std_logic_vector(4 downto 0); 
signal rWA : std_logic_vector(4 downto 0);

begin

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' then
            reg_if_id(63 downto 32) <= Instruction;
            reg_if_id(31 downto 0) <= PCp4;
        end if;
    end if;
end process;

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' then
            reg_id_ex(157 downto 152) <= func;
            reg_id_ex(151 downto 147) <= sa;
            reg_id_ex(146 downto 142) <= rd;
            reg_id_ex(141 downto 137) <= rt;
            reg_id_ex(136 downto 105) <= Ext_imm;
            reg_id_ex(104 downto 73) <= RD2;
            reg_id_ex(72 downto 41) <= RD1;
            reg_id_ex(40 downto 9) <= reg_if_id(31 downto 0);
            reg_id_ex(8) <= RegDst;
            reg_id_ex(7) <= ALUSrc;
            reg_id_ex(6 downto 4) <= ALUOp;
            reg_id_ex(3) <= Branch;
            reg_id_ex(2) <= MemWrite;
            reg_id_ex(1) <= RegWrite;
            reg_id_ex(0) <= MemtoReg;
        end if;
    end if;
end process;

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' then
            reg_ex_mem(105 downto 101) <= rWA;
            reg_ex_mem(100 downto 69) <= reg_id_ex(104 downto 73);
            reg_ex_mem(68 downto 37) <= ALURes;
            reg_ex_mem(36) <= zero;
            reg_ex_mem(35 downto 4) <= BranchAddress;
            reg_ex_mem(3) <= reg_id_ex(3);
            reg_ex_mem(2) <= reg_id_ex(2);
            reg_ex_mem(1) <= reg_id_ex(1);
            reg_ex_mem(0) <= reg_id_ex(0);
        end if;
    end if;
end process;

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' then
            reg_mem_wb(70 downto 66) <= reg_ex_mem(105 downto 101);
            reg_mem_wb(65 downto 34) <= ALURes1;
            reg_mem_wb(33 downto 2) <= MemData;
            reg_mem_wb(1) <= reg_ex_mem(1);
            reg_mem_wb(0) <= reg_ex_mem(0);
        end if;
    end if;
end process;

    monopulse : MPG port map(en, btn(0), clk);
    
    -- main units
    inst_IFetch : IFetch port map(clk, btn(1), en, reg_ex_mem(35 downto 4), JumpAddress, Jump, PCSrc, Instruction, PCp4);
    inst_ID : ID port map(clk, en, reg_if_id(57 downto 32), WD, reg_mem_wb(1), reg_mem_wb(70 downto 66), rt, rd, ExtOp, RD1, RD2, Ext_imm, func, sa);
    inst_UC : UC port map(reg_if_id(63 downto 58), RegDst, ExtOp, ALUSrc, Branch, Jump, ALUOp, MemWrite, MemtoReg, RegWrite);
    inst_EX : EX port map(reg_id_ex(40 downto 9), reg_id_ex(72 downto 41), reg_id_ex(104 downto 73), reg_id_ex(136 downto 105), reg_id_ex(157 downto 152), 
                          reg_id_ex(151 downto 147), reg_id_ex(7), reg_id_ex(6 downto 4), BranchAddress, ALURes, Zero, reg_id_ex(141 downto 137), 
                          reg_id_ex(146 downto 142), reg_id_ex(8), rWA); 
    inst_MEM : MEM port map(clk, en, reg_ex_mem(68 downto 37), reg_ex_mem(100 downto 69), reg_ex_mem(2), MemData, ALURes1);

    -- Write-Back unit 
    WD <= reg_mem_wb(33 downto 2) when reg_mem_wb(0) = '1' else reg_mem_wb(65 downto 34); 

    -- branch control
    PCSrc <= reg_ex_mem(36) and reg_ex_mem(3);

    -- jump address
    JumpAddress <= reg_if_id(31 downto 28) & reg_if_id(57 downto 32) & "00";

   -- SSD display MUX
    with sw(7 downto 5) select
        digits <=  Instruction when "000", 
                   PCp4 when "001",
                   reg_id_ex(72 downto 41) when "010",
                   reg_id_ex(104 downto 73) when "011",
                   reg_id_ex(136 downto 105) when "100",
                   ALURes when "101",
                   MemData when "110",
                   WD when "111",
                   (others => 'X') when others; 
    
    -- main controls on the leds
    led(10 downto 0) <= ALUOp & RegDst & ExtOp & ALUSrc & Branch & Jump & MemWrite & MemtoReg & RegWrite;
    
    display : SSD port map(clk, digits, an, cat);
    
end Behavioral;
